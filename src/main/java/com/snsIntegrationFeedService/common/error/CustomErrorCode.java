package com.snsIntegrationFeedService.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CustomErrorCode {
	POST_ID_NOT_FOUND(HttpStatus.BAD_REQUEST.value(),
			"존재하지 않는 게시글 아이디입니다."),
	USER_ALREADY_EXIST(HttpStatus.BAD_REQUEST.value(),
			"이미 존재하는 사용자입니다."),
	CONTAIN_PRIVATE_INFORMATION(HttpStatus.BAD_REQUEST.value(),
			"비밀번호에 개인정보를 포함할 수 없습니다."),
	LACK_OF_CHARACTERS(HttpStatus.BAD_REQUEST.value(),
			"비밀번호는 10자 이상이어야 합니다."),
	NOT_AVAILABLE_COMMON_PASSWORD(HttpStatus.BAD_REQUEST.value(),
			"통상적으로 사용되는 비밀번호는 사용할 수 없습니다."),
	NOT_FOLLOW_RULES(HttpStatus.BAD_REQUEST.value(),
			"숫자, 문자, 특수문자 중 2가지 이상을 포함해야 합니다."),
	NOT_AVAIABLE_DATE(HttpStatus.BAD_REQUEST.value(),
			"시작일자와 종료일자가 유효하지 않습니다"),
	NOT_THREE_CONSECUTIVE(HttpStatus.BAD_REQUEST.value(),
			"3회 이상 연속되는 문자 사용이 불가합니다.");

	private final int errorCode;
	private final String errorMessage;
}
