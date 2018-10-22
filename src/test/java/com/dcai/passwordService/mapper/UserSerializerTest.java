package com.dcai.passwordService.mapper;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringWriter;

import org.junit.Before;
import org.junit.Test;

import com.dcai.passwordService.model.PasswdField;
import com.dcai.passwordService.model.User;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

public class UserSerializerTest {

	private UserSerializer userSerializer;

	private static final String EXPECTED_SERIALIZATION = "{\"name\":\"userName\",\"uid\":1234,\"gid\":7809,"
	        + "\"comment\":\"userInfo\",\"home\":\"homeDirectory\",\"shell\":\"commandShell\"}";

	@Before
	public void setUp() throws Exception {
		userSerializer = new UserSerializer();
	}

	@Test
	public void serializeShouldSucceed() throws IOException {
		User user = new User();
		user.putAttribute(PasswdField.UserName, "userName");
		user.putAttribute(PasswdField.UserID, "1234");
		user.putAttribute(PasswdField.GroupID, "7809");
		user.putAttribute(PasswdField.UserInfo, "userInfo");
		user.putAttribute(PasswdField.HomeDirectory, "homeDirectory");
		user.putAttribute(PasswdField.CommandShell, "commandShell");

		JsonFactory factory = new JsonFactory();

		StringWriter stringWriter = new StringWriter();
		JsonGenerator generator = factory.createGenerator(stringWriter);
		userSerializer.serialize(user, generator, null);
		generator.close();

		assertEquals("failed to serial user.", EXPECTED_SERIALIZATION, stringWriter.toString());
	}

}
