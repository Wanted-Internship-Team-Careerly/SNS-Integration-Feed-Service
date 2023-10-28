package com.snsIntegrationFeedService.post.service;

import com.snsIntegrationFeedService.post.entity.PostTypeEnum;
import java.util.List;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
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
    private WebClient webClient;
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

    @Transactional
    public PostTypeEnum postLike(String postId, boolean isTest) {
        webClient =  WebClient.builder().build();
        Post post = postRepository.findByPostId(postId).orElseThrow(
            () -> new CustomException(CustomErrorCode.POST_ID_NOT_FOUND)
        );
        HttpStatusCode statusCode = HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value());
        PostTypeEnum postType = post.getType();
        switch (postType) {
            //API 호출 부분
            case TWITTER:
                statusCode = webClient.get()
                    .uri("https://www.twitter.com/likes/" + postId)
                    .retrieve()
                    .toEntity(String.class)
                    .block()
                    .getStatusCode();
                break;
            case FACEBOOK:
                statusCode = webClient.get()
                    .uri("https://www.facebook.com/likes/" + postId)
                    .retrieve()
                    .toEntity(String.class)
                    .block()
                    .getStatusCode();
                break;
            case THREADS:
                statusCode = webClient.get()
                    .uri("https://www.threads.com/likes/" + postId)
                    .retrieve()
                    .toEntity(String.class)
                    .block()
                    .getStatusCode();
                break;
            case INSTAGRAM:
                statusCode = webClient.get()
                    .uri("https://www.instagram.com/likes/" + postId)
                    .retrieve()
                    .toEntity(String.class)
                    .block()
                    .getStatusCode();
                break;
        }
        if (statusCode.value() == HttpStatus.OK.value() || isTest) {
            post.like();
            return postType;
        }
        else {
            throw new CustomException(CustomErrorCode.URL_NOT_FOUND);
        }
    }
}