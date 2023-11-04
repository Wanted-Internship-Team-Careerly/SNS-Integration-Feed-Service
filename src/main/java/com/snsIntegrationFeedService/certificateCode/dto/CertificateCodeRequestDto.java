package com.snsIntegrationFeedService.certificateCode.dto;

import lombok.Getter;

@Getter
public class CertificateCodeRequestDto {
	private String account;
	private String password;
	private int verificationCode;
}
