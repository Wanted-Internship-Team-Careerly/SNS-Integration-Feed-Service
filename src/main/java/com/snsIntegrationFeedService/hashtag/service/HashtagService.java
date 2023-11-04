package com.snsIntegrationFeedService.hashtag.service;

import com.snsIntegrationFeedService.hashtag.entity.Hashtag;
import com.snsIntegrationFeedService.hashtag.repository.HashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HashtagService {

    private final HashtagRepository hashtagRepository;

    public Hashtag createHashtag(String tag) {
        Hashtag hashtag = Hashtag.builder().name(tag).build();

        return hashtagRepository.save(hashtag);
    }

    public Long getHashtagId(String hashtag) {
        return hashtagRepository.findIdByName(hashtag);
    }
}
