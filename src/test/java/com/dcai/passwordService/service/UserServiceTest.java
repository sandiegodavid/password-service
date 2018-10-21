package com.dcai.passwordService.service;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.dcai.passwordService.repository.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepository;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(userService);
	}

	@Test
	public void getAllUsersShouldCallRepository() {
		userService.getAllUsers();
		verify(userRepository).getAllUsers();
	}

}
