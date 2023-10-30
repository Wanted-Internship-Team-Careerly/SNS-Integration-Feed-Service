package com.snsIntegrationFeedService.post.repository;

import static com.snsIntegrationFeedService.post.entity.QPost.*;
import static com.snsIntegrationFeedService.postHashtag.entity.QPostHashtag.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.snsIntegrationFeedService.hashtag.entity.QHashtag;
import com.snsIntegrationFeedService.post.entity.Post;
import com.snsIntegrationFeedService.post.entity.PostTypeEnum;
import com.snsIntegrationFeedService.post.entity.QPost;
import com.snsIntegrationFeedService.postHashtag.entity.QPostHashtag;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class PostRepositoryImpl implements PostRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	@Override
	public List<Post> findWithFilter(
		String hashtag, String type, String orderBy, String sortBy, String searchBy, String search,
		int pageCount, int page, String account
	) {
		QPost qPost = post;
		QPostHashtag qPostHashtag = postHashtag;
		QHashtag qHashtag = QHashtag.hashtag;

		JPAQuery<Post> query = queryFactory.selectFrom(qPost)
			.leftJoin(qPost.postHashtagList, qPostHashtag).fetchJoin()
			.leftJoin(qPostHashtag.hashtag, qHashtag).fetchJoin();

		// 해시태그
		if (hashtag != null) {
			query.where(qHashtag.name.eq(hashtag));
		} else {
			query.where(qHashtag.name.eq(account));
		}

		// 타입
		if (type != null) {
			query.where(qPost.type.eq(PostTypeEnum.valueOf(type)));
		}

		// 정렬
		OrderSpecifier<?> orderSpecifier = getOrderSpecifier(qPost, orderBy, sortBy);

		// 검색
		BooleanExpression searchExpression = getSearchExpression(qPost, searchBy, search);

		return query.orderBy(orderSpecifier)
			.where(searchExpression)
			.offset((long)page * pageCount)
			.limit(pageCount)
			.fetch();
	}

	private OrderSpecifier<?> getOrderSpecifier(QPost post, String orderBy, String sortBy) {
		Map<String, OrderSpecifier<?>> orderMap = new HashMap<>();
		orderMap.put("created_at", sortBy.equals("desc") ? post.createdAt.desc() : post.createdAt.asc());
		orderMap.put("updated_at", sortBy.equals("desc") ? post.modifiedAt.desc() : post.modifiedAt.asc());
		orderMap.put("like_count", sortBy.equals("desc") ? post.likeCount.desc() : post.likeCount.asc());
		orderMap.put("share_count", sortBy.equals("desc") ? post.shareCount.desc() : post.shareCount.asc());
		orderMap.put("view_count", sortBy.equals("desc") ? post.viewCount.desc() : post.viewCount.asc());
		return orderMap.getOrDefault(orderBy, orderMap.get("created_at"));
	}

	private BooleanExpression getSearchExpression(QPost qPost, String searchBy, String search) {
		if ("title".equals(searchBy)) {
			return qPost.title.contains(search);
		} else if ("content".equals(searchBy)) {
			return qPost.content.contains(search);
		} else {
			return qPost.title.contains(search).or(qPost.content.contains(search));
		}
	}
}