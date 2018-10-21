package com.dcai.passwordService.repository;

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

import com.dcai.passwordService.exception.FileError;
import com.dcai.passwordService.model.PasswdField;
import com.dcai.passwordService.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UserRepository.class })
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	private static final String TEST_PASSWORD_FILE = "src/test/resources/testPasswd.txt";

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
		assertEquals("failed to get all users.", 5, users.size());
	}

	@Test
	public void getAllUsersShouldThrowException() {
		ReflectionTestUtils.setField(userRepository, "passwdFile", "non exist file");

		thrown.expect(FileError.class);
		userRepository.getAllUsers();
	}

}
