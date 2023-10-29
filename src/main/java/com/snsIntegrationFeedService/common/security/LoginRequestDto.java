package com.snsIntegrationFeedService.common.security;

import lombok.Getter;

@Getter
public class LoginRequestDto {
	private String account;
	private String password;
}
