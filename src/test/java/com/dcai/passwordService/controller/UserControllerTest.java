package com.dcai.passwordService.controller;

import static org.mockito.Mockito.*;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.dcai.passwordService.service.UserService;

import edu.emory.mathcs.backport.java.util.Collections;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

	@InjectMocks
	private UserController userController;

	@Mock
	private UserService userService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(userController);
	}

	@Test
	public void getAllUsersShouldCallUserService() {
		userController.getAllUsers();
		verify(userService).getAllUsers();
	}

	@Test
	public void getUsersShouldCallUserService() {
		Map<String, String> query = Collections.emptyMap();
		userController.getUsers(query);
		verify(userService).getUsers(query);
	}

}
