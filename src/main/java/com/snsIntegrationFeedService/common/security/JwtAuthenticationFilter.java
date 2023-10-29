package com.snsIntegrationFeedService.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snsIntegrationFeedService.common.dto.ApiResponseDto;
import com.snsIntegrationFeedService.user.dto.LoginRequestDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final JwtUtil jwtUtil;

	public JwtAuthenticationFilter(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
		setFilterProcessesUrl("/api/users/login");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		log.info("로그인 시도");
		try {
			LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

			return getAuthenticationManager().authenticate(
					new UsernamePasswordAuthenticationToken(
							requestDto.getAccount(),
							requestDto.getPassword(),
							null
					)
			);
		} catch (IOException e) {
			log.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request,
											HttpServletResponse response,
											FilterChain chain,
											Authentication authResult) throws IOException {
		log.info("로그인 성공 및 JWT 생성");
		String account = ((UserDetailsImpl) authResult.getPrincipal()).getAccount();

		String token = jwtUtil.createToken(account);
		response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);

		response.setStatus(200);
		response.setContentType("application/json");
		String result = new ObjectMapper().writeValueAsString(
				new ApiResponseDto(HttpStatus.OK.value(), "login success")
		);

		response.getOutputStream().print(result);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request,
											  HttpServletResponse response,
											  AuthenticationException failed) throws IOException {
		log.info("로그인 실패");

		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		response.setContentType("application/json");
		String result = new ObjectMapper().writeValueAsString(
				new ApiResponseDto(HttpStatus.BAD_REQUEST.value(), "login failure")
		);

		response.getOutputStream().print(result);
	}
}
