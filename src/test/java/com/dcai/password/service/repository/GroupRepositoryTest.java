package com.dcai.password.service.repository;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.dcai.password.service.exception.FileError;
import com.dcai.password.service.model.Group;
import com.dcai.password.service.model.GroupField;

import edu.emory.mathcs.backport.java.util.Collections;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { GroupRepository.class })
public class GroupRepositoryTest {

	@Autowired
	private GroupRepository groupRepository;

	static final String TEST_GROUP_FILE = "src/test/resources/testGroup.txt";

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void initShouldSetupDefaults() {
		CSVFormat csvFormat = (CSVFormat) ReflectionTestUtils.getField(groupRepository, "csvFormat");
		String[] headers = new String[GroupField.values().length];
		headers = Arrays.asList(GroupField.values()).stream()
		        .map(GroupField::name)
		        .collect(Collectors.toList())
		        .toArray(headers);
		assertEquals("init failed to setup field delimiter",
		        GroupRepository.DEFAULT_FIELD_DELIMITER.charAt(0),
		        csvFormat.getDelimiter());
		assertEquals("init failed to setup comment marker",
		        GroupRepository.DEFAULT_COMMENT_MARKER.charAt(0),
		        csvFormat.getCommentMarker().charValue());
		assertArrayEquals("init failed to setup header",
		        headers,
		        csvFormat.getHeader());
	}

	@Test
	public void getAllGroupsShouldSucceed() {
		ReflectionTestUtils.setField(groupRepository, "groupsFile", TEST_GROUP_FILE);
		List<Group> groups = groupRepository.getAllGroups();
		assertEquals("failed to get all groups.", 8, groups.size());
	}

	@Test
	public void getAllGroupsShouldThrowException() {
		ReflectionTestUtils.setField(groupRepository, "groupsFile", "non exist file");

		thrown.expect(FileError.class);
		groupRepository.getAllGroups();
	}

	@Test
	public void findGroupsShouldSucceed() {
		ReflectionTestUtils.setField(groupRepository, "groupsFile", TEST_GROUP_FILE);
		MultiValueMap<String, String> query = new LinkedMultiValueMap<>();
		query.add("gid", "1001");
		query.add("member", "dwoodlins");
		query.add("members", "dcai");
		List<Group> groups = groupRepository.findGroups(query);
		assertEquals("failed to findUsers.", 1, groups.size());
	}

	@Test
	public void findGroupsWithNoAliasShouldSucceed() {
		ReflectionTestUtils.setField(groupRepository, "groupsFile", TEST_GROUP_FILE);
		MultiValueMap<String, String> query = new LinkedMultiValueMap<>();
		query.add("gid", "1001");
		query.add("members", "dwoodlins");
		query.add("members", "dcai");
		List<Group> groups = groupRepository.findGroups(query);
		assertEquals("failed to findUsers.", 1, groups.size());
	}

	@Test
	public void selectGroupShouldMatchEmptyQuery() {
		MultiValueMap<String, String> query = new LinkedMultiValueMap<>();
		Group group = new Group();
		assertTrue("failed to select group", GroupRepository.selectGroup(group, query));
	}

	@Test
	public void selectGroupShouldMatchNullQueryField() {
		MultiValueMap<String, String> query = new LinkedMultiValueMap<>();
		query.put("name", null);
		Group groups = new Group();
		assertTrue("failed to select group", GroupRepository.selectGroup(groups, query));
	}

	@Test
	public void selectGroupShouldMatchListField() {
		MultiValueMap<String, String> query = new LinkedMultiValueMap<>();
		query.add("members", "group");
		query.add("members", "list");
		Group groups = new Group();
		groups.putAttribute(GroupField.GroupList, "group");
		groups.putAttribute(GroupField.GroupList, "list");
		groups.putAttribute(GroupField.GroupList, "someone else");
		assertTrue("failed to select group", GroupRepository.selectGroup(groups, query));
	}

	@Test
	public void selectGroupShouldMatchListFieldWith1() {
		MultiValueMap<String, String> query = new LinkedMultiValueMap<>();
		query.add("members", "list");
		Group groups = new Group();
		groups.putAttribute(GroupField.GroupList, "group");
		groups.putAttribute(GroupField.GroupList, "list");
		groups.putAttribute(GroupField.GroupList, "someone else");
		assertTrue("failed to select group", GroupRepository.selectGroup(groups, query));
	}

	@Test
	public void selectGroupShouldMatchEmptyQueryField() {
		MultiValueMap<String, String> query = new LinkedMultiValueMap<>();
		query.put("name", Collections.emptyList());
		Group groups = new Group();
		assertTrue("failed to select group", GroupRepository.selectGroup(groups, query));
	}

	@Test
	public void selectGroupShouldMatchNullQuery() {
		MultiValueMap<String, String> query = new LinkedMultiValueMap<>();
		query.add("name", null);
		Group groups = new Group();
		assertTrue("failed to select group", GroupRepository.selectGroup(groups, query));
	}

	@Test
	public void selectGroupShouldExcludeMissingAttr() {
		MultiValueMap<String, String> query = new LinkedMultiValueMap<>();
		query.add("name", "name");
		Group group = new Group();
		assertFalse("failed to exlude group", GroupRepository.selectGroup(group, query));
	}

	@Test
	public void selectGroupShouldExcludeNullField() {
		MultiValueMap<String, String> query = new LinkedMultiValueMap<>();
		query.add("name", "name");
		Group group = new Group();
		group.putAttribute(GroupField.GroupName, null);
		assertFalse("failed to exlude group", GroupRepository.selectGroup(group, query));
	}

	@Test
	public void selectGroupShouldExcludeMismatch() {
		MultiValueMap<String, String> query = new LinkedMultiValueMap<>();
		query.add("name", "name");
		Group group = new Group();
		group.putAttribute(GroupField.GroupName, "name diff");
		assertFalse("failed to exlude group", GroupRepository.selectGroup(group, query));
	}

}
