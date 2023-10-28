package com.snsIntegrationFeedService.user.service;

import com.snsIntegrationFeedService.common.error.CustomErrorCode;
import com.snsIntegrationFeedService.common.exception.CustomException;
import com.snsIntegrationFeedService.user.dto.SignupRequestDto;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class PasswordValidation {
	String account;
	String email;
	String password;
	private final CommonPassword commonPassword;

	public PasswordValidation(CommonPassword commonPassword) {
		this.commonPassword = commonPassword;
	}

	public void validatePassword(SignupRequestDto requestDto) {
		this.account = requestDto.getAccount();
		this.email = requestDto.getEmail();
		this.password = requestDto.getPassword();

		// 다른 개인 정보와 유사한 비밀번호는 사용할 수 없다.
		if (containPrivateInformation()) {
			throw new CustomException(CustomErrorCode.CONTAIN_PRIVATE_INFORMATION);
		}

		// 비밀번호는 최소 10자 이상이어야 한다.
		if (password.length() < 10) {
			throw new CustomException(CustomErrorCode.LACK_OF_CHARACTERS);
		}

		// 숫자, 문자, 특수문자 중 2가지 이상을 포함해야 합니다.
		if (!followRules()) {
			throw new CustomException(CustomErrorCode.NOT_FOLLOW_RULES);
		}

		// 3회 이상 연속되는 문자 사용이 불가합니다.
		if (isThreeConsecutiveLetters()) {
			throw new CustomException(CustomErrorCode.NOT_THREE_CONSECUTIVE);
		}

		// 통상적으로 자주 사용되는 비밀번호는 사용할 수 없습니다.
		if (commonPassword.isCommonPassword(password)) {
			throw new CustomException(CustomErrorCode.NOT_AVAILABLE_COMMON_PASSWORD);
		}
	}

	private boolean containPrivateInformation() {
		int atIndex = email.indexOf("@");
		String id = email.substring(0, atIndex);

		return password.contains(account) || password.contains(id);
	}

	private boolean followRules() {
		boolean numberEnglish = Pattern.matches("^(?=.*[0-9]+)(?=.*[a-zA-Z]+).+", password);
		boolean englishSpecialSymbol = Pattern.matches("^(?=.*[a-zA-Z]+)(?=.*[!@#$%^&*]+).+", password);
		boolean specialSymbolsNumber = Pattern.matches("^(?=.*[!@#$%^&*]+)(?=.*[0-9]+).+", password);

		return numberEnglish || englishSpecialSymbol || specialSymbolsNumber;
	}

	private boolean isThreeConsecutiveLetters() {
		int count = 1;
		for (int i = 1; i < password.length(); i++) {
			if (password.charAt(i - 1) == password.charAt(i)) {
				count++;
			} else {
				count = 1;
			}
			if (count == 3) {
				return true;
			}
		}
		return false;
	}

}
