package com.test.demo.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.test.demo.model.Response;
import com.test.demo.model.User;
import com.test.demo.repository.UserRepository;
import com.test.demo.utils.UserForm;

public class UserServiceTest {

	private static final String TEST_EMAIL = "test@test.com";

	@InjectMocks
	UserService userService;

	@Mock
	UserRepository userRepo;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testInvalidEmail() {
		boolean result = userService.emailValid("test");
		assertFalse(result);
	}

	@Test
	public void testInvalidPassword() {
		boolean result = userService.passwordValid("test");
		assertFalse(result);
	}

	@Test
	public void testSignupOK() {
		UserForm userForm = new UserForm();
		userForm.setEmail(TEST_EMAIL);
		userForm.setPassword("Password123");
		Response response = userService.signup(userForm);
		assertTrue(response.getError() != null);
	}

	@Test
	public void testLoginOK() {
		String token = "token";
		User user = new User();
		user.setEmail(TEST_EMAIL);
		Mockito.when(userRepo.findByToken(Mockito.anyString())).thenReturn(user);
		User response = userService.login(token, TEST_EMAIL);
		assertTrue(response.getEmail() != null && response.getEmail().equals(TEST_EMAIL));
	}
}
