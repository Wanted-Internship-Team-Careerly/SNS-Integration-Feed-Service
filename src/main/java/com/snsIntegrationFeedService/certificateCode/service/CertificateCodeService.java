package com.snsIntegrationFeedService.certificateCode.service;

import com.snsIntegrationFeedService.certificateCode.dto.CertificateCodeRequestDto;
import com.snsIntegrationFeedService.certificateCode.entity.CertificateCode;
import com.snsIntegrationFeedService.certificateCode.repository.CertificateCodeRepository;
import com.snsIntegrationFeedService.common.error.CustomErrorCode;
import com.snsIntegrationFeedService.common.exception.CustomException;
import com.snsIntegrationFeedService.user.entity.User;
import com.snsIntegrationFeedService.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CertificateCodeService {
	private final UserService userService;
	private final CertificateCodeRepository certificateCodeRepository;
	private final PasswordEncoder passwordEncoder;

	public void requestMembershipApproval(CertificateCodeRequestDto requestDto) {
		String account = requestDto.getAccount();
		String password = requestDto.getPassword();
		Integer verificationCode = requestDto.getVerificationCode();

		// password 확인
		User user = userService.findUser(account);
		if (!user.getPassword().equals(passwordEncoder.encode(password))) {
			throw new CustomException(CustomErrorCode.PASSWORD_NOT_MATCH);
		}

		// 승인 코드 확인
		CertificateCode certificateCode = certificateCodeRepository.findByUser(user).orElseThrow(
				() -> new CustomException(CustomErrorCode.CERTIFICATECODE_NOT_FOUND)
		);
		if (!certificateCode.getCode().equals(verificationCode)) {
			throw new CustomException(CustomErrorCode.CERTIFICATECODE_NOT_MATCH);
		}

		user.approveUser();
	}

}
