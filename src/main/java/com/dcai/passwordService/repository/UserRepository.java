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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.dcai.passwordService.exception.FileError;
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

	private CSVFormat csvFormat;

	@PostConstruct
	public void init() {
		csvFormat = CSVFormat.DEFAULT.withDelimiter(fieldDelimiter).withCommentMarker(commentMarker)
		        .withHeader(PasswdField.class);
		log.debug("passwdFile:({}), csvFormat:{}", passwdFile, csvFormat);
	}

	public List<User> getAllUsers() {
		List<User> users = new ArrayList<>();
		try (Reader reader = new BufferedReader(new FileReader(passwdFile))) {
			CSVParser parser = csvFormat.parse(reader);
			for (CSVRecord record : parser) {
				User user = new User();
				Map<PasswdField, String> fields = user.getFields();
				for (PasswdField field : PasswdField.values()) {
					if (PasswdField.Password == field) {
						// don't read password
						continue;
					}
					fields.put(field, record.get(field));
				}
				users.add(user);
			}
		} catch (IOException e) {
			log.error("Passwd file:({}) not found or can't be parsed.", passwdFile, e);
			throw new FileError();
		}
		log.debug("returning {} users.", users.size());
		return users;
	}

}
