package com.snsIntegrationFeedService.user.service;

import com.snsIntegrationFeedService.certificateCode.entity.CertificateCode;
import com.snsIntegrationFeedService.certificateCode.repository.CertificateCodeRepository;
import com.snsIntegrationFeedService.certificateCode.service.CertificateCodeService;
import com.snsIntegrationFeedService.common.error.CustomErrorCode;
import com.snsIntegrationFeedService.common.exception.CustomException;
import com.snsIntegrationFeedService.user.dto.SignupRequestDto;
import com.snsIntegrationFeedService.user.entity.User;
import com.snsIntegrationFeedService.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final CertificateCodeRepository certificateCodeRepository;
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

	public void issueCertificateCode(SignupRequestDto requestDto) {
		User user = findUser(requestDto.getAccount());
		Integer code = generateCode();
		CertificateCode certificateCode = CertificateCode.builder()
				.Code(code).user(user).build();
		certificateCodeRepository.save(certificateCode);
	}

	public User findUser(String account) {
		return userRepository.findByAccount(account).orElse(null);
	}

	private Integer generateCode() {
		Random random = new Random();
		int code = 0;
		int place = 1;
		while (code / 100000 == 0) {
			int num = random.nextInt(9) + 1;
			System.out.println("num = " + num);
			code += num * place;
			place *= 10;
		}
		return code;
	}
}
