package com.snsIntegrationFeedService.post.entity;

import com.snsIntegrationFeedService.common.entity.Timestamped;
import com.snsIntegrationFeedService.postHashtag.entity.PostHashtag;
import com.snsIntegrationFeedService.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

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
	private String content;

	@Column(nullable = false)
	private Long viewCount;

	@Column(nullable = false)
	private Long shareCount;

	@OneToMany(mappedBy = "post", orphanRemoval = true)
	private List<PostHashtag> postHashtagList = new ArrayList<>();
}
