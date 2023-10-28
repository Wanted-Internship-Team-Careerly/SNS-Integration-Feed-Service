package com.snsIntegrationFeedService.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Configuration
public class QuerydslConfig {

	private final EntityManager em;

	@Bean
	public JPAQueryFactory queryFactory() {
		return new JPAQueryFactory(em);
	}
}
