package com.snsIntegrationFeedService.user.service;

import com.snsIntegrationFeedService.common.error.CustomErrorCode;
import com.snsIntegrationFeedService.common.exception.CustomException;
import com.snsIntegrationFeedService.user.dto.SignupRequestDto;
import com.snsIntegrationFeedService.user.entity.User;
import com.snsIntegrationFeedService.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final PasswordValidation passwordValidation;

	public void signup(SignupRequestDto requestDto) {
		String account = requestDto.getAccount();
		String email = requestDto.getEmail();
		String password = requestDto.getPassword();

		// account로 검증하여 회원이 존재하면 예외 발생
		User targetUser = findUser(account);
		if (targetUser != null) {
			throw new CustomException(CustomErrorCode.USER_ALREADY_EXIST);
		}

		// 비밀번호 검증이 완료되면 password encoding
		passwordValidation.validatePassword(requestDto);
		password = passwordEncoder.encode(password);

		User user = User.builder()
				.account(account).email(email)
				.password(password).build();
		userRepository.save(user);
	}

	private User findUser(String account) {
		return userRepository.findByAccount(account).orElse(null);
	}

}
