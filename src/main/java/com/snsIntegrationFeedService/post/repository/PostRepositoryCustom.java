package com.snsIntegrationFeedService.post.repository;

import java.util.List;

import com.snsIntegrationFeedService.common.security.UserDetailsImpl;
import com.snsIntegrationFeedService.post.entity.Post;

public interface PostRepositoryCustom {

	List<Post> findWithFilter(
		String hashtag, String type, String orderBy, String sortBy, String searchBy, String search,
		int pageCount, int page,
		UserDetailsImpl userDetails);
}