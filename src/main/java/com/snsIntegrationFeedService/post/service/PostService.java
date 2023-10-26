package com.snsIntegrationFeedService.post.service;

import com.snsIntegrationFeedService.common.error.CustomErrorCode;
import com.snsIntegrationFeedService.common.exception.CustomException;
import com.snsIntegrationFeedService.post.entity.Post;
import com.snsIntegrationFeedService.post.entity.PostTypeEnum;
import com.snsIntegrationFeedService.post.repository.PostRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public PostTypeEnum postLike(String content_id) {
        Optional<Post> post = postRepository.findByPostId(content_id);
        if (!post.isPresent()) {
            throw new CustomException(CustomErrorCode.POST_NOT_FOUND);
        }
        PostTypeEnum postType = post.get().getType();
        switch (postType) {
            //API 호출 부분
            case TWITTER:
                ;
            case FACEBOOK:
                ;
            case THREADS:
                ;
            case INSTAGRAM:
                ;

        }
        return postType;
    }
}