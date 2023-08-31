package com.cheong.clinic_api.auth;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.cheong.clinic_api.user.service.IUserService;

@SpringBootTest
public class UserServiceTest {

	@Autowired
	private IUserService userService;

	@Test
	public void throwUserNotFound() {
		assertDoesNotThrow(() -> userService.loadByUsername("zzz"));
	}

	@Test
	public void assertUserEqualsToUsername() {

		String username = "020828140563";

		assertEquals(userService.findByUsername(username).getId(), username);
	}

	@Test
	public void assertThrowNoSuchElementd() {
		assertThrows(NoSuchElementException.class, () -> userService.findByUsername("2020000380"));
	}
}
