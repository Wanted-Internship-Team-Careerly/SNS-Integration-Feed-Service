package com.snsIntegrationFeedService.postHashtag.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.snsIntegrationFeedService.hashtag.entity.Hashtag;
import com.snsIntegrationFeedService.post.entity.Post;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class PostHashtag {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	private Post post;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hashtag_id")
	private Hashtag hashtag;
}
