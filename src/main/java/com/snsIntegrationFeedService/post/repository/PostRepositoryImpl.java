package com.snsIntegrationFeedService.post.repository;

import static com.snsIntegrationFeedService.hashtag.entity.QHashtag.hashtag;
import static com.snsIntegrationFeedService.post.entity.QPost.*;
import static com.snsIntegrationFeedService.postHashtag.entity.QPostHashtag.*;

import com.snsIntegrationFeedService.post.dto.request.StaticsRequestDto;
import com.snsIntegrationFeedService.post.entity.QPost;
import com.snsIntegrationFeedService.postHashtag.entity.QPostHashtag;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
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
		QHashtag qHashtag = QHashtag.hashtag;

		JPAQuery<Post> query = queryFactory.selectFrom(post)
			.leftJoin(post.postHashtagList, postHashtag)
			.leftJoin(postHashtag.hashtag, qHashtag);

		// 해시태그
		if (hashtag != null) {
			query.where(qHashtag.name.eq(hashtag));
		} else {
			query.where(qHashtag.name.eq(account));
		}

		// 타입
		if (type != null) {
			query.where(post.type.eq(PostTypeEnum.valueOf(type)));
		}

		// 정렬
		OrderSpecifier<?> orderSpecifier = getOrderSpecifier(orderBy, sortBy);

		// 검색
		BooleanExpression searchExpression = getSearchExpression(searchBy, search);

		return query.orderBy(orderSpecifier)
			.where(searchExpression)
			.offset((long)page * pageCount)
			.limit(pageCount)
			.fetch();
	}

	// todo
	// 1. hashtag의 id를 name=request.getHashtag() 를 통해 가져온다
	// 2. post_hashtag 테이블에서 해당 hashtag_id를 가진 post를 가져온다
	// 3. 해당 post들중 기간에 맞는 post의 개수를 출력한다
	@Override
	public int findByStaticsRequest(StaticsRequestDto request, Date date) {

		String hashtagName = request.getHashtag(); // request에서 hashtag 이름을 가져옵니다.
		// 기간의 시작 시간을 설정합니다.
		LocalDateTime startOfDay = date.toInstant().atZone(ZoneId.systemDefault())
				.toLocalDate().atStartOfDay(ZoneId.systemDefault()).toLocalDateTime();
		// 기간의 종료 시간을 설정합니다.
		LocalDateTime endOfDay = date.toInstant().atZone(ZoneId.systemDefault())
				.toLocalDate().atTime(23, 59, 59);

		Long hashtagId = queryFactory.select(hashtag.id)
				.from(hashtag)
				.where(hashtag.name.eq(hashtagName))
				.fetchOne();

		return queryFactory.selectFrom(post)
				.innerJoin(postHashtag)
				.on(post.id.eq(postHashtag.post.id))
				.where(postHashtag.hashtag.id.eq(hashtagId)
						.and(post.createdAt.between(startOfDay, endOfDay)))
				.fetch().size();

	}

	private OrderSpecifier<?> getOrderSpecifier(String orderBy, String sortBy) {
		Map<String, OrderSpecifier<?>> orderMap = new HashMap<>();
		orderMap.put("created_at", sortBy.equals("desc") ? post.createdAt.desc() : post.createdAt.asc());
		orderMap.put("updated_at", sortBy.equals("desc") ? post.modifiedAt.desc() : post.modifiedAt.asc());
		orderMap.put("like_count", sortBy.equals("desc") ? post.likeCount.desc() : post.likeCount.asc());
		orderMap.put("share_count", sortBy.equals("desc") ? post.shareCount.desc() : post.shareCount.asc());
		orderMap.put("view_count", sortBy.equals("desc") ? post.viewCount.desc() : post.viewCount.asc());
		return orderMap.getOrDefault(orderBy, orderMap.get("created_at"));
	}

	private BooleanExpression getSearchExpression(String searchBy, String search) {
		if ("title".equals(searchBy)) {
			return post.title.contains(search);
		} else if ("content".equals(searchBy)) {
			return post.content.contains(search);
		} else {
			return post.title.contains(search).or(post.content.contains(search));
		}
	}
}