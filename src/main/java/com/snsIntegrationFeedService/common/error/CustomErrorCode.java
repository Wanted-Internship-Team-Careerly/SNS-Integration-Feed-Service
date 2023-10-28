package com.snsIntegrationFeedService.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CustomErrorCode {
	// 예시 에러코드입니다.
	USER_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "존재하지 않는 사용자입니다."),

	POST_ID_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "존재하지 않는 게시글 아이디입니다."),
	;

	private final int errorCode;
	private final String errorMessage;
}
