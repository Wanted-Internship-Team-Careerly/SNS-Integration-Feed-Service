package com.snsIntegrationFeedService.user.service;

import java.util.Arrays;
import java.util.List;

public class CommonPassword {
	private final List<String> commonPasswordList;

	public CommonPassword() {
		String[] commonPasswordArray = {
				"col123456", "D1lakiss", "110110jp", "Gizli", "abc123", "azerty",
				"1q2w3e4r", "pass@123", "qwerty123", "123qwe", "a1b2c3", "Groupd2013",
				"1q2w3e", "Liman1000", "1qaz2wsx", "password1", "mar20lt", "abcd1234",
				"luzit2000", "1234qwer", "qwe123", "super123", "qwer1234", "123456789a",
				"823477aA", "q1w2e3r4", "123456a", "asdf1234", "123abc", "asd123",
				"lol123", "a123456", "qwerty1", "Indya123", "anmol123", "1qazxsw2",
				"12345qwert", "q1w2e3", "zaq12wsx", "password123", "wow12345", "12qwaszx",
				"Pass@123", "passw0rd", "Password1"};
		this.commonPasswordList = Arrays.asList(commonPasswordArray);
	}

	public boolean isCommonPassword(String password) {
		return commonPasswordList.contains(password);
	}
}
