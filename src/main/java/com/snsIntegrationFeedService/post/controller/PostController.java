package com.snsIntegrationFeedService.post.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.snsIntegrationFeedService.common.security.UserDetailsImpl;
import com.snsIntegrationFeedService.post.dto.PostDetailResponseDto;
import com.snsIntegrationFeedService.post.dto.PostsResponseDto;
import com.snsIntegrationFeedService.post.service.PostService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Post API", description = "Post 관련 API 정보를 담고 있습니다.")
@RequiredArgsConstructor
@RestController
public class PostController {

	private final PostService postService;

	@Operation(summary = "게시글 상세보기", description = "유저가 게시물을 클릭 시 사용되는 API")
	@GetMapping("/api/post/{postId}")
	public ResponseEntity<PostDetailResponseDto> getPostDetail(@PathVariable String postId) {
		PostDetailResponseDto postDetailResponseDto = postService.getPostDetail(postId);
		return ResponseEntity.ok().body(postDetailResponseDto);
	}

	@Operation(summary = "게시글 목록", description = "유저가 검색한 게시글 목록을 보는 API")
	@GetMapping("/api/posts")
	public ResponseEntity<?> getPosts(
		@RequestParam(required = false) String hashtag,
		@RequestParam(required = false) String type,
		@RequestParam(name = "order_by", defaultValue = "created_at", required = false) String orderBy,
		@RequestParam(name = "sort_by", defaultValue = "desc", required = false) String sortBy,
		@RequestParam(name = "search_by", required = false) String searchBy,
		@RequestParam(required = false) String search,
		@RequestParam(name = "page_count", defaultValue = "10", required = false) int pageCount,
		@RequestParam(name = "page", defaultValue = "0", required = false) int page,
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		PostsResponseDto posts =
			postService.getPosts(hashtag, type, orderBy, sortBy, searchBy, search, pageCount, page,
				userDetails.getUser().getAccount());
		return ResponseEntity.ok().body(posts);
	}
}
