package com.dcai.password.service.repository;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import com.dcai.password.service.exception.FileError;
import com.dcai.password.service.model.Group;
import com.dcai.password.service.model.PasswdField;
import com.dcai.password.service.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UserRepository.class, GroupRepository.class })
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private GroupRepository groupRepository;

	static final String TEST_PASSWORD_FILE = "src/test/resources/testPasswd.txt";

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void initShouldSetupDefaults() {
		CSVFormat csvFormat = (CSVFormat) ReflectionTestUtils.getField(userRepository, "csvFormat");
		String[] headers = new String[PasswdField.values().length];
		headers = Arrays.asList(PasswdField.values()).stream()
		        .map(PasswdField::name)
		        .collect(Collectors.toList())
		        .toArray(headers);
		assertEquals("init failed to setup field delimiter",
		        UserRepository.DEFAULT_FIELD_DELIMITER.charAt(0),
		        csvFormat.getDelimiter());
		assertEquals("init failed to setup comment marker",
		        UserRepository.DEFAULT_COMMENT_MARKER.charAt(0),
		        csvFormat.getCommentMarker().charValue());
		assertArrayEquals("init failed to setup header",
		        headers,
		        csvFormat.getHeader());
	}

	@Test
	public void getAllUsersShouldSucceed() {
		ReflectionTestUtils.setField(userRepository, "passwdFile", TEST_PASSWORD_FILE);
		List<User> users = userRepository.getAllUsers();
		assertEquals("failed to get all users.", 6, users.size());
	}

	@Test
	public void getAllUsersShouldThrowException() {
		ReflectionTestUtils.setField(userRepository, "passwdFile", "non exist file");

		thrown.expect(FileError.class);
		userRepository.getAllUsers();
	}

	@Test
	public void findUsersShouldSucceed() {
		ReflectionTestUtils.setField(userRepository, "passwdFile", TEST_PASSWORD_FILE);
		Map<String, String> query = new HashMap<>();
		query.put("name", "dwoodlins");
		query.put("uid", "1001");
		List<User> users = userRepository.findUsers(query);
		assertEquals("failed to findUsers.", 1, users.size());
	}

	@Test
	public void selectUserShouldMatchEmptyQuery() {
		Map<String, String> query = new HashMap<>();
		User user = new User();
		assertTrue("failed to select user", UserRepository.selectUser(user, query));
	}

	@Test
	public void selectUserShouldMatchNullQuery() {
		Map<String, String> query = new HashMap<>();
		query.put("name", null);
		User user = new User();
		assertTrue("failed to select user", UserRepository.selectUser(user, query));
	}

	@Test
	public void selectUserShouldExcludeNullField() {
		Map<String, String> query = new HashMap<>();
		query.put("name", "name");
		User user = new User();
		user.putAttribute(PasswdField.UserName, null);
		assertFalse("failed to exlude user", UserRepository.selectUser(user, query));
	}

	@Test
	public void selectUserShouldExcludeMismatch() {
		Map<String, String> query = new HashMap<>();
		query.put("name", "name");
		User user = new User();
		user.putAttribute(PasswdField.UserName, "name diff");
		assertFalse("failed to exlude user", UserRepository.selectUser(user, query));
	}

	@Test
	public void findUserGroupsShouldSucceed() {
		ReflectionTestUtils.setField(groupRepository, "groupsFile", GroupRepositoryTest.TEST_GROUP_FILE);
		User user = new User();
		user.putAttribute(PasswdField.UserName, "dcai");
		List<Group> users = userRepository.findUserGroups(user);
		assertEquals("failed to get user groups.", 2, users.size());
	}
}
