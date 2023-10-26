package com.snsIntegrationFeedService.post.controller;

import com.snsIntegrationFeedService.post.entity.Post;
import com.snsIntegrationFeedService.post.entity.PostTypeEnum;
import com.snsIntegrationFeedService.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.snsIntegrationFeedService.common.dto.ApiResponseDto;
import com.snsIntegrationFeedService.common.exception.CustomException;
import com.snsIntegrationFeedService.common.error.CustomErrorCode;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @Operation(summary = "좋아요 남기기", description = "content_id를 사용하여 특정 게시물에 대한 좋아요 "
        + "요청, 해당 컨트롤러는 SNS Type별 API를 사용하여 좋아요 요청을 수행하고 결과(성공, NOT_FOUND) 반환")
    @PostMapping("/api/post/like/{content_id}")
    public ResponseEntity<ApiResponseDto> updatePostLike(@PathVariable String content_id)
        throws NotFoundException {
        PostTypeEnum postType = postService.postLike(content_id);
        return ResponseEntity.ok().body(
            new ApiResponseDto(HttpStatus.OK.value(), postType.toString() + " 게시물 좋아요 완료")
        );
    }
}
