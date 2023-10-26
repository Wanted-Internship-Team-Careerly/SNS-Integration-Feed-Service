package com.snsIntegrationFeedService.post.controller;

import com.snsIntegrationFeedService.post.dto.PostDetailResponseDto;
import com.snsIntegrationFeedService.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PostController {

  private final PostService postService;

  @Operation(summary = "게시글 상세보기", description = "유저가 게시물을 클릭 시 사용되는 API")
  @GetMapping("/api/post/{postId}")
  public ResponseEntity<?> getPostDetail(@PathVariable String postId) {
    PostDetailResponseDto postDetailResponseDto = postService.getPostDetail(postId);
    return ResponseEntity.ok().body(postDetailResponseDto);
  }
}
