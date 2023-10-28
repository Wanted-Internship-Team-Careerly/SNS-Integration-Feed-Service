package com.snsIntegrationFeedService.hashtag.entity;

import com.snsIntegrationFeedService.postHashtag.entity.PostHashtag;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Hashtag {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String name;

	@OneToMany(mappedBy = "hashtag", orphanRemoval = true)
	private List<PostHashtag> postHashtagList = new ArrayList<>();

	@Builder
	public Hashtag(String name) {
		this.name = name;
	}

	public Hashtag() {

	}
}
