package com.snsIntegrationFeedService.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snsIntegrationFeedService.common.dto.ApiResponseDto;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j(topic = "JWT 검증 및 인가")
public class JwtAuthorizationFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
	private final UserDetailsServiceImpl userDetailsService;

	public JwtAuthorizationFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req,
									HttpServletResponse res,
									FilterChain filterChain) throws ServletException, IOException {
		// Header에서 jwt 토큰 받아오기
		String tokenValue = jwtUtil.getTokenFromRequest(req);

		if (StringUtils.hasText(tokenValue)) {

			if (notValidate(res, tokenValue)) return;

			Claims info = getClaims(res, tokenValue);
			if (info == null) return;

			if (verifyAuthentication(info)) return;
		}

		filterChain.doFilter(req, res);
	}


	private boolean notValidate(HttpServletResponse res, String tokenValue) throws IOException {
		if (!jwtUtil.validateToken(tokenValue)) {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			res.setContentType("application/json");
			String result = new ObjectMapper().writeValueAsString(
					new ApiResponseDto(HttpStatus.BAD_REQUEST.value(), "INVALID_TOKEN")
			);

			res.getOutputStream().print(result);
			return true;
		}
		return false;
	}

	private Claims getClaims(HttpServletResponse res, String tokenValue) throws IOException {
		Claims info;

		try {
			info = jwtUtil.getUserInfoFromToken(tokenValue);
		} catch (Exception e) {
			// JWT 검증에 실패한 경우 처리
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			res.setContentType("application/json");
			String result = new ObjectMapper().writeValueAsString(
					new ApiResponseDto(HttpStatus.BAD_REQUEST.value(), "INVALID_TOKEN")
			);

			res.getOutputStream().print(result);
			return null;
		}
		return info;
	}

	private boolean verifyAuthentication(Claims info) {
		try {
			setAuthentication(info.getSubject());
		} catch (Exception e) {
			// 인증 처리에 실패한 경우 처리
			log.error(e.getMessage());
			return true;
		}
		return false;
	}

	// 인증 처리
	public void setAuthentication(String account) {
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		Authentication authentication = createAuthentication(account);
		context.setAuthentication(authentication);

		SecurityContextHolder.setContext(context);
	}

	// 인증 객체 생성
	private Authentication createAuthentication(String account) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(account);
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}
}
