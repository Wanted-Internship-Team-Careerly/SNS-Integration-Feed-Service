package com.snsIntegrationFeedService.user.entity;

import com.snsIntegrationFeedService.certificateCode.entity.CertificateCode;
import com.snsIntegrationFeedService.post.entity.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
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
	@Builder.Default
	private Boolean isAccessed = false;

	@OneToMany(mappedBy = "user", orphanRemoval = true)
	private List<Post> postList = new ArrayList<>();

	@OneToOne(mappedBy = "user")
	private CertificateCode certificateCode;

	public void approveUser() {
		this.isAccessed = true;
	}

	@Builder
	public User(String account, String password, String email) {
		this.account = account;
		this.password = password;
		this.email = email;
	}

	public User() {

	}
}
