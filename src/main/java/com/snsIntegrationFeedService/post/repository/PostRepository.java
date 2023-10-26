package com.snsIntegrationFeedService.post.repository;
import com.snsIntegrationFeedService.post.entity.Post;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByPostId(String ContentId);
}
