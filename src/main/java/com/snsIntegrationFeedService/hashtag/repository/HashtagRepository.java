package com.snsIntegrationFeedService.hashtag.repository;

import com.snsIntegrationFeedService.hashtag.entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

    Long findIdByName(String hashtag);
}
