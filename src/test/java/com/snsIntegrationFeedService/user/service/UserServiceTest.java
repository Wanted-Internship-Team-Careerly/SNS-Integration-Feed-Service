package com.snsIntegrationFeedService.user.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.snsIntegrationFeedService.certificateCode.entity.CertificateCode;
import com.snsIntegrationFeedService.certificateCode.repository.CertificateCodeRepository;
import com.snsIntegrationFeedService.common.error.CustomErrorCode;
import com.snsIntegrationFeedService.common.exception.CustomException;
import com.snsIntegrationFeedService.user.dto.SignupRequestDto;
import com.snsIntegrationFeedService.user.entity.User;
import com.snsIntegrationFeedService.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private CertificateCodeRepository certificateCodeRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private PasswordValidation passwordValidation;

	@Test
	void 회원가입_test() throws Exception {
		// given
		SignupRequestDto signupRequestDto = SignupRequestDto.builder()
			.account("test")
			.email("test@test.com")
			.password("test123")
			.build();

		// stub 1
		when(userRepository.findByAccount(any())).thenReturn(Optional.empty());

		// stub 2
		when(passwordEncoder.encode(any())).thenReturn("encodedPassword");

		// when
		userService.signup(signupRequestDto);

		// then
		verify(userRepository).save(any(User.class));
		verify(certificateCodeRepository).save(any(CertificateCode.class));
	}

	@Test
	void 회원가입_실패_중복_test() throws Exception {
		// given
		SignupRequestDto signupRequestDto = SignupRequestDto.builder()
			.account("test")
			.email("test@test.com")
			.password("test123")
			.build();

		User user = User.builder()
			.account("test")
			.email("test@test.com")
			.password("test123")
			.build();

		// stub 1
		when(userRepository.findByAccount(any())).thenReturn(Optional.of(user));

		// when
		CustomException exception = assertThrows(CustomException.class, () -> {
			userService.signup(signupRequestDto);
		});

		// then
		assertEquals(CustomErrorCode.USER_ALREADY_EXIST, exception.getErrorCode());
	}
}