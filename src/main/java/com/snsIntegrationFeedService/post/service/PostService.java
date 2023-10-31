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
import com.snsIntegrationFeedService.common.security.UserDetailsImpl;
import com.snsIntegrationFeedService.post.dto.PostDetailResponseDto;
import com.snsIntegrationFeedService.post.dto.PostsResponseDto;
import com.snsIntegrationFeedService.post.entity.Post;
import com.snsIntegrationFeedService.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClientResponseException;

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
        webClient = WebClient.builder().build();
        Post post = postRepository.findByPostId(postId).orElseThrow(
            () -> new CustomException(CustomErrorCode.POST_ID_NOT_FOUND)
        );
        HttpStatusCode statusCode = HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value());
        PostTypeEnum postType = post.getType();
        switch (postType) {
            //API 호출 부분
            case TWITTER:
                try {
                    statusCode = webClient.get()
                        .uri("https://www.twitter.com/likes/" + postId)
                        .retrieve()
                        .toEntity(String.class)
                        .block()
                        .getStatusCode();
                } catch (WebClientResponseException.NotFound e) {
                    statusCode = HttpStatus.NOT_FOUND;
                }
                break;
            case FACEBOOK:
                try {
                    statusCode = webClient.get()
                        .uri("https://www.facebook.com/likes/" + postId)
                        .retrieve()
                        .toEntity(String.class)
                        .block()
                        .getStatusCode();
                } catch (WebClientResponseException.NotFound e) {
                    statusCode = HttpStatus.NOT_FOUND;
                }
                break;
            case THREADS:
                try {
                    statusCode = webClient.get()
                        .uri("https://www.threads.com/likes/" + postId)
                        .retrieve()
                        .toEntity(String.class)
                        .block()
                        .getStatusCode();
                } catch (WebClientResponseException.NotFound e) {
                    statusCode = HttpStatus.NOT_FOUND;
                }
                break;
            case INSTAGRAM:
                try {
                    statusCode = webClient.get()
                        .uri("https://www.instagram.com/likes/" + postId)
                        .retrieve()
                        .toEntity(String.class)
                        .block()
                        .getStatusCode();
                } catch (WebClientResponseException.NotFound e) {
                    statusCode = HttpStatus.NOT_FOUND;
                }
                break;
        }
        if (statusCode.value() == HttpStatus.OK.value() || isTest) {
            post.like();
            return postType;
        } else {
            throw new CustomException(CustomErrorCode.URL_NOT_FOUND);
        }
    }

    @Transactional(readOnly = true)
    public PostsResponseDto getPosts(
        String hashtag, String type, String orderBy, String sortBy, String searchBy, String search,
        int pageCount, int page, UserDetailsImpl userDetails) {
        List<Post> posts = postRepository.findWithFilter(
            hashtag, type, orderBy, sortBy, searchBy, search, pageCount, page,
            userDetails.getAccount()
        );

        List<PostDetailResponseDto> postDetailResponseDtos = posts.stream()
            .map(post -> {
                List<String> hashTags = post.getPostHashtagList().stream()
                    .map(postHashtag -> postHashtag.getHashtag().getName())
                    .toList();
                return PostDetailResponseDto.from(post, hashTags);
            })
            .toList();

        return PostsResponseDto.from(postDetailResponseDtos, pageCount, page);
    }
}
