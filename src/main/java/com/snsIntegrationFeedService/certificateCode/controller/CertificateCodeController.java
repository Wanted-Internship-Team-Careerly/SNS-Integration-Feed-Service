package com.snsIntegrationFeedService.certificateCode.controller;

import com.snsIntegrationFeedService.certificateCode.dto.CertificateCodeRequestDto;
import com.snsIntegrationFeedService.certificateCode.service.CertificateCodeService;
import com.snsIntegrationFeedService.common.dto.ApiResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "승인코드 API", description = "승인코드와 관련된 API 정보를 담고 있습니다.")
public class CertificateCodeController {
	private final CertificateCodeService certificateCodeService;

	@Operation(summary = "승인 요청", description = "가입 승인에 필요한 정보를 받아 검증한 뒤 유저의 가입을 승인합니다.")
	@PostMapping("/verifications")
	public ResponseEntity<ApiResponseDto> requestMembershipApproval(@RequestBody CertificateCodeRequestDto requestDto) {
		certificateCodeService.requestMembershipApproval(requestDto);
		return ResponseEntity.ok().body(new ApiResponseDto(HttpStatus.OK.value(), "가입 승인 완료"));
	}


}
