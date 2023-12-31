package com.snsIntegrationFeedService.common.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {
	public static final String AUTHORIZATION_HEADER = "Authorization";        // Header KEY 값
	public static final String BEARER_PREFIX = "Bearer ";        // Token 식별자
	private static final long TOKEN_TIME = 30 * 60 * 1000L;        // 토큰 만료시간: 30 분

	@Value("${jwt.secret.key}") // Base64 Encode 한 SecretKey
	private String secretKey;
	private Key key;
	private static final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

	// 로그 설정
	public static final Logger logger = LoggerFactory.getLogger("JWT 관련 로그");

	@PostConstruct
	public void init() {
		byte[] bytes = Base64.getDecoder().decode(secretKey);
		key = Keys.hmacShaKeyFor(bytes);
	}

	// JWT 생성
	public String createToken(String account) {
		Date date = new Date();

		return BEARER_PREFIX +
				Jwts.builder()
						.setSubject(account) // 사용자 식별자값
						.setExpiration(new Date(date.getTime() + TOKEN_TIME)) // 만료 시간
						.setIssuedAt(date) // 발급일
						.signWith(key, signatureAlgorithm) // 암호화 알고리즘
						.compact();
	}

	// HttpServletRequest 에서 Header Value : JWT 가져오기
	public String getTokenFromRequest(HttpServletRequest req) {
		String bearerToken = req.getHeader(AUTHORIZATION_HEADER);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
			return bearerToken.substring(BEARER_PREFIX.length());
		}
		return null;
	}

	// 토큰 검증
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch (SecurityException | MalformedJwtException e) {
			logger.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
		} catch (ExpiredJwtException e) {
			logger.error("Expired JWT token, 만료된 JWT token 입니다.");
		} catch (UnsupportedJwtException e) {
			logger.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
		}
		return false;
	}

	// 토큰에서 사용자 정보 가져오기
	public Claims getUserInfoFromToken(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}
}
