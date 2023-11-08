package com.snsIntegrationFeedService.post.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.snsIntegrationFeedService.common.entity.Timestamped;
import com.snsIntegrationFeedService.postHashtag.entity.PostHashtag;
import com.snsIntegrationFeedService.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Post extends Timestamped {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Column(nullable = false)
	private String postId;

	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	private PostTypeEnum type;

	@Column(nullable = false)
	private String title;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String content;

	@Column(nullable = false)
	private Long viewCount;

	@Column(nullable = false)
	private Long likeCount;

	@Column(nullable = false)
	private Long shareCount;

	@JsonManagedReference
	@OneToMany(mappedBy = "post", orphanRemoval = true)
	private List<PostHashtag> postHashtagList = new ArrayList<>();

	public void view() {
		this.viewCount++;
	}

	@Builder
	public Post(User user, PostTypeEnum type, String title, String content) {
		this.user = user;
		this.postId = UUID.randomUUID().toString();
		this.type = type;
		this.title = title;
		this.content = content;
		this.viewCount = 0L;
		this.likeCount = 0L;
		this.shareCount = 0L;
	}

	public Post() {

	}
}
