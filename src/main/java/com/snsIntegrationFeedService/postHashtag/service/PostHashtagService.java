package com.snsIntegrationFeedService.postHashtag.service;

import com.snsIntegrationFeedService.hashtag.entity.Hashtag;
import com.snsIntegrationFeedService.hashtag.service.HashtagService;
import com.snsIntegrationFeedService.post.entity.Post;
import com.snsIntegrationFeedService.postHashtag.entity.PostHashtag;
import com.snsIntegrationFeedService.postHashtag.repository.PostHashtagRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostHashtagService {

    private final PostHashtagRepository postHashtagRepository;
    private final HashtagService hashtagService;

    public PostHashtag createPostHashtag(Post post, Hashtag hashtag) {
        PostHashtag postHashtag = PostHashtag.builder()
                .post(post)
                .hashtag(hashtag)
                .build();
        return postHashtagRepository.save(postHashtag);
    }

    public List<Long> getPostIdsByHashtag(String hashtag) {
        Long hashtagId = hashtagService.getHashtagId(hashtag);

        return postHashtagRepository.findPostIdsByHashtagId(hashtagId);
    }
}
