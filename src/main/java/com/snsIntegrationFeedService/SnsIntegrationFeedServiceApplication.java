package com.snsIntegrationFeedService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SnsIntegrationFeedServiceApplication extends SpringBootServletInitializer {

	public static void main(String[] args)  {
		SpringApplication.run(SnsIntegrationFeedServiceApplication.class, args);
	}

}
