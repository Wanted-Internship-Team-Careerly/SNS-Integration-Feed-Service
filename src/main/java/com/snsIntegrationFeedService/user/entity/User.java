package com.snsIntegrationFeedService.user.entity;

import com.snsIntegrationFeedService.certificateCode.entity.CertificateCode;
import com.snsIntegrationFeedService.post.entity.Post;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String account;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private Boolean isAccessed = false;

	@OneToMany(mappedBy = "user", orphanRemoval = true)
	private List<Post> postList = new ArrayList<>();

	@OneToOne(mappedBy = "user")
	private CertificateCode certificateCode;
}
