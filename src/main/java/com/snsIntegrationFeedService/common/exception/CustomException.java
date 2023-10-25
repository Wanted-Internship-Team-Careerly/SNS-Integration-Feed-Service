package com.snsIntegrationFeedService.common.exception;

import com.snsIntegrationFeedService.common.error.CustomErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CustomException extends RuntimeException {
	private final CustomErrorCode errorCode;

	public CustomException(CustomErrorCode errorCode) {
		super(errorCode.getErrorMessage());
		this.errorCode = errorCode;
	}
}
