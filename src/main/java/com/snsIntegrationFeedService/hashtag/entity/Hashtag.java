package com.snsIntegrationFeedService.hashtag.entity;

import java.util.ArrayList;
import java.util.List;

import com.snsIntegrationFeedService.postHashtag.entity.PostHashtag;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Hashtag {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String name;

	@OneToMany(mappedBy = "hashtag", orphanRemoval = true)
	private final List<PostHashtag> postHashtagList = new ArrayList<>();

	@Builder
	public Hashtag(String name) {
		this.name = name;
	}

	public Hashtag() {

	}
}
