package com.snsIntegrationFeedService.post.controller;

import com.snsIntegrationFeedService.common.dto.ApiResponseDto;
import com.snsIntegrationFeedService.post.entity.PostTypeEnum;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.snsIntegrationFeedService.post.dto.PostDetailResponseDto;
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

    @Operation(summary = "좋아요 남기기", description = "content_id를 사용하여 특정 게시물에 대한 좋아요 "
        + "요청, 해당 컨트롤러는 SNS Type별 API를 사용하여 좋아요 요청을 수행하고 결과(성공, NOT_FOUND) 반환")
    @PostMapping("/api/post/like/{postId}")
    public ResponseEntity<ApiResponseDto> updatePostLike(@PathVariable String postId) {
        boolean isLikeAPITest = true;
        PostTypeEnum postType = postService.postLike(postId, isLikeAPITest);
        return ResponseEntity.ok().body(
            new ApiResponseDto(HttpStatus.OK.value(), postType.toString() + " 게시물 좋아요 완료")
        );
    }
}
