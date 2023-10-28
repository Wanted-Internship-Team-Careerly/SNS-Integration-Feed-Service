package com.snsIntegrationFeedService.user.service;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class CommonPassword {
	private final List<String> commonPasswordList;

	public CommonPassword() {
		String[] commonPasswordArray = {"Groupd2013", "123456789a", "12345qwert", "password123"};
		this.commonPasswordList = Arrays.asList(commonPasswordArray);
	}

	public boolean isCommonPassword(String password) {
		return commonPasswordList.contains(password);
	}
}
