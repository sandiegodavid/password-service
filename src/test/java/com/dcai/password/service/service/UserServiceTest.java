package com.dcai.password.service.service;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;

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
import com.dcai.password.service.repository.UserRepository;

import edu.emory.mathcs.backport.java.util.Collections;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepository;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(userService);
	}

	@Test
	public void getAllUsersShouldCallRepository() {
		userService.getAllUsers();
		verify(userRepository).getAllUsers();
	}

	@Test
	public void getUsersShouldCallRepository() {
		Map<String, String> query = Collections.emptyMap();
		userService.getUsers(query);
		verify(userRepository).findUsers(query);
	}

	@Test
	public void getUserShouldCallRepository() {
		when(userRepository.findUsers(any(Map.class))).thenReturn(Collections.emptyList());
		Optional<User> result = userService.getUser(5);
		verify(userRepository).findUsers(any(Map.class));
		assertFalse("failed to get user with none", result.isPresent());
	}

	@Test
	public void getUserShouldCallRepositoryAndGet() {
		when(userRepository.findUsers(any(Map.class))).thenReturn(Arrays.asList(new User()));
		Optional<User> result = userService.getUser(5);
		assertTrue("failed to get user", result.isPresent());
	}

	@Test
	public void getUserGroupsShouldCallRepository() {
		when(userRepository.findUsers(any(Map.class))).thenReturn(Arrays.asList(new User()));
		userService.getUserGroups(5);
		verify(userRepository).findUserGroups(any(User.class));
	}

	@Test
	public void getUserGroupsShouldThrow() {
		thrown.expect(RecordMissingError.class);
		userService.getUserGroups(5);
	}

}
