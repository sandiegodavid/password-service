package com.dcai.passwordService.repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.dcai.passwordService.exception.FileError;
import com.dcai.passwordService.model.Group;
import com.dcai.passwordService.model.GroupField;
import com.dcai.passwordService.model.PasswdField;
import com.dcai.passwordService.model.User;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class UserRepository {

	static final String DEFAULT_PASSWD_FILE = "/etc/passwd";
	static final String DEFAULT_FIELD_DELIMITER = ":";
	static final String DEFAULT_COMMENT_MARKER = "#";

	@Value("${file.password:" + DEFAULT_PASSWD_FILE + "}")
	private String passwdFile;

	@Value("${file.passwd.field.delimiter:" + DEFAULT_FIELD_DELIMITER + "}")
	private char fieldDelimiter;

	@Value("${file.passwd.commentMarker:" + DEFAULT_COMMENT_MARKER + "}")
	private char commentMarker;

	@Autowired
	private GroupRepository groupRepository;

	private CSVFormat csvFormat;

	@PostConstruct
	public void init() {
		csvFormat = CSVFormat.DEFAULT.withDelimiter(fieldDelimiter).withCommentMarker(commentMarker)
		        .withHeader(PasswdField.class);
		log.debug("passwdFile:({}), csvFormat:{}", passwdFile, csvFormat);
	}

	public List<User> getAllUsers() {
		return findUsers(null);
	}

	public List<User> findUsers(Map<String, String> query) {
		List<User> users = new ArrayList<>();
		try (Reader reader = new BufferedReader(new FileReader(passwdFile))) {
			CSVParser parser = csvFormat.parse(reader);
			for (CSVRecord record : parser) {
				User user = new User();
				for (PasswdField field : PasswdField.values()) {
					if (PasswdField.Password == field) {
						// don't read password
						continue;
					}
					user.putAttribute(field, record.get(field));
				}
				if (selectUser(user, query)) {
					users.add(user);
				}
			}
		} catch (IOException e) {
			log.error("Passwd file:({}) not found or can't be parsed.", passwdFile, e);
			throw new FileError();
		}
		log.debug("returning {} users.", users.size());
		return users;
	}

	static boolean selectUser(User user, Map<String, String> query) {
		if (query == null || query.isEmpty()) {
			return true;
		}
		for (PasswdField f : PasswdField.values()) {
			for (Map.Entry<String, String> e : query.entrySet()) {
				if (e.getKey().contentEquals(f.getField())) {
					String queryValue = e.getValue();
					if (queryValue == null) {
						break;
					}
					Object attr = user.getAttribute(f);
					if ((attr == null) || !queryValue.contentEquals(attr.toString())) {
						return false;
					}
					break;
				}
			}
		}
		return true;
	}

	public List<Group> findUserGroups(User user) {
		String userName = (String) user.getAttribute(PasswdField.UserName);
		MultiValueMap<String, String> query = new LinkedMultiValueMap<>();
		query.add(GroupField.GroupList.getField(), userName);
		return groupRepository.findGroups(query);
	}

}
