package com.snsIntegrationFeedService.post.dto.request;

import com.snsIntegrationFeedService.post.entity.Post;
import com.snsIntegrationFeedService.post.entity.PostTypeEnum;
import com.snsIntegrationFeedService.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreatePostRequest {

    private PostTypeEnum type;

    private String title;

    private String content;

    private String hashtag;


    public Post toEntity(User user, CreatePostRequest request) {
        return Post.builder()
                .user(user)
                .type(request.type)
                .title(request.title)
                .content(request.content)
                .build();
    }

}
