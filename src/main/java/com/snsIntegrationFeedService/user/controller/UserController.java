package com.snsIntegrationFeedService.user.controller;

import com.snsIntegrationFeedService.common.dto.ApiResponseDto;
import com.snsIntegrationFeedService.user.dto.SignupRequestDto;
import com.snsIntegrationFeedService.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "사용자 API", description = "사용자와 관련된 API 정보를 담고 있습니다.")
public class UserController {
	private final UserService userService;

	@Operation(summary = "회원 가입", description = "가입에 필요한 정보를 받아 회원가입합니다.")
	@PostMapping("/signup")
	public ResponseEntity<ApiResponseDto> signup(@Valid @RequestBody SignupRequestDto requestDto) {
		userService.signup(requestDto);
		return ResponseEntity.ok().body(new ApiResponseDto(HttpStatus.OK.value(), "회원 가입 완료"));
	}


}
