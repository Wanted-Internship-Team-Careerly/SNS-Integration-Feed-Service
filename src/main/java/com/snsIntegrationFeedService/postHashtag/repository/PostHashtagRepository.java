package com.snsIntegrationFeedService.postHashtag.repository;

import com.snsIntegrationFeedService.postHashtag.entity.PostHashtag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostHashtagRepository extends JpaRepository<PostHashtag, Long> {

    @Query(value = "SELECT post_id FROM post_hashtag WHERE hashtag_id = :hashtagId", nativeQuery = true)
    List<Long> findPostIdsByHashtagId(@Param("hashtagId") Long hashtagId);
}
