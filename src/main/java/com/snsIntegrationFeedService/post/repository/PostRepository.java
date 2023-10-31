package com.snsIntegrationFeedService.post.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.snsIntegrationFeedService.post.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

	Optional<Post> findByPostId(String postId);
}