package com.dcai.password.service.service;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.dcai.password.service.model.Group;
import com.dcai.password.service.repository.GroupRepository;

import edu.emory.mathcs.backport.java.util.Collections;

@RunWith(MockitoJUnitRunner.class)
public class GroupServiceTest {

	@InjectMocks
	private GroupService groupService;

	@Mock
	private GroupRepository groupRepository;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(groupService);
	}

	@Test
	public void getAllGroupsShouldCallRepository() {
		groupService.getAllGroups();
		verify(groupRepository).getAllGroups();
	}

	@Test
	public void getGroupsShouldCallRepository() {
		MultiValueMap<String, String> query = new LinkedMultiValueMap<>();
		groupService.getGroups(query);
		verify(groupRepository).findGroups(query);
	}

	@Test
	public void getGroupShouldCallRepository() {
		when(groupRepository.findGroups(any(MultiValueMap.class))).thenReturn(Collections.emptyList());
		Optional<Group> result = groupService.getGroup(5);
		verify(groupRepository).findGroups(any(MultiValueMap.class));
		assertFalse("failed to get group with none", result.isPresent());
	}

	@Test
	public void getGroupShouldCallRepositoryAndGet() {
		when(groupRepository.findGroups(any(MultiValueMap.class))).thenReturn(Arrays.asList(new Group()));
		Optional<Group> result = groupService.getGroup(5);
		assertTrue("failed to get group", result.isPresent());
	}

}
