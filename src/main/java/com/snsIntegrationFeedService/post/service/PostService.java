package com.snsIntegrationFeedService.post.service;

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
}
