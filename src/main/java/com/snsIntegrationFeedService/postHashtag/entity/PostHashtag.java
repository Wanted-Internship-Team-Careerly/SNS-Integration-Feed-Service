package com.snsIntegrationFeedService.postHashtag.entity;

import com.snsIntegrationFeedService.hashtag.entity.Hashtag;
import com.snsIntegrationFeedService.post.entity.Post;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class PostHashtag {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	private Post post;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hashtag_id")
	private Hashtag hashtag;

	@Builder
	public PostHashtag(Post post, Hashtag hashtag) {
		this.post = post;
		this.hashtag = hashtag;
	}

	public PostHashtag() {

	}
}
