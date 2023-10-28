package com.snsIntegrationFeedService.post.service;

import com.snsIntegrationFeedService.hashtag.entity.Hashtag;
import com.snsIntegrationFeedService.hashtag.service.HashtagService;
import com.snsIntegrationFeedService.post.dto.request.CreatePostRequest;
import com.snsIntegrationFeedService.postHashtag.service.PostHashtagService;
import com.snsIntegrationFeedService.user.entity.User;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snsIntegrationFeedService.common.error.CustomErrorCode;
import com.snsIntegrationFeedService.common.exception.CustomException;
import com.snsIntegrationFeedService.post.dto.PostDetailResponseDto;
import com.snsIntegrationFeedService.post.entity.Post;
import com.snsIntegrationFeedService.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

	private final PostRepository postRepository;

	private final HashtagService hashtagService;
	private final PostHashtagService postHashtagService;

	@Transactional
	public PostDetailResponseDto getPostDetail(String postId) {
		// 예외 처리
		Post post = postRepository.findByPostId(postId).orElseThrow(
			() -> new CustomException(CustomErrorCode.POST_ID_NOT_FOUND)
		);

		// 해시 태그명 가져오기
		List<String> hashTags = post.getPostHashtagList().stream()
			.map(postHashtag -> postHashtag.getHashtag().getName())
			.toList();

		// 조회수 증가
		post.view();
		return PostDetailResponseDto.from(post, hashTags);
	}

	public Post createPost(User user, CreatePostRequest request) {
		// post 생성
		Post savedPost = postRepository.save(request.toEntity(user, request));

		// hashtag 생성
		Hashtag hashtag = hashtagService.createHashtag(request.getHashtag());

		// postHashtag 생성
		postHashtagService.createPostHashtag(savedPost, hashtag);

		return savedPost;
	}
}
