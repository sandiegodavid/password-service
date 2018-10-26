package com.dcai.password.service.controller;

import static org.mockito.Mockito.*;

import java.util.Map;
import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.dcai.password.service.exception.RecordMissingError;
import com.dcai.password.service.model.User;
import com.dcai.password.service.service.UserService;

import edu.emory.mathcs.backport.java.util.Collections;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

	@InjectMocks
	private UserController userController;

	@Mock
	private UserService userService;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

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

	@Test
	public void getUserShouldCallUserService() {
		when(userService.getUser(5)).thenReturn(Optional.of(new User()));
		userController.getUser(5);
		verify(userService).getUser(5);
	}

	@Test
	public void getUserShouldThrow() {
		thrown.expect(RecordMissingError.class);
		userController.getUser(5);
		verify(userService).getUser(5);
	}

	@Test
	public void getUserGroupsShouldCallUserService() {
		when(userService.getUserGroups(5)).thenReturn(Collections.emptyList());
		userController.getUserGroups(5);
		verify(userService).getUserGroups(5);
	}

}
