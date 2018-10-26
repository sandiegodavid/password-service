package com.dcai.password.service.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.dcai.password.service.exception.RecordMissingError;
import com.dcai.password.service.model.Group;
import com.dcai.password.service.service.GroupService;

@RunWith(MockitoJUnitRunner.class)
public class GroupControllerTest {

	@InjectMocks
	private GroupController groupController;

	@Mock
	private GroupService groupService;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(groupController);
	}

	@Test
	public void getAllUsersShouldCallUserService() {
		groupController.getAllGroups();
		verify(groupService).getAllGroups();
	}

	@Test
	public void getUsersShouldCallUserService() {
		MultiValueMap<String, String> query = new LinkedMultiValueMap<>();
		groupController.getGroups(query);
		verify(groupService).getGroups(query);
	}

	@Test
	public void getUserShouldCallUserService() {
		when(groupService.getGroup(5)).thenReturn(Optional.of(new Group()));
		groupController.getGroup(5);
		verify(groupService).getGroup(5);
	}

	@Test
	public void getUserShouldThrow() {
		thrown.expect(RecordMissingError.class);
		groupController.getGroup(5);
		verify(groupService).getGroup(5);
	}

}
