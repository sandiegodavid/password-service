package com.dcai.passwordService.mapper;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringWriter;

import org.junit.Before;
import org.junit.Test;

import com.dcai.passwordService.model.Group;
import com.dcai.passwordService.model.GroupField;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

public class GroupSerializerTest {

	private GroupSerializer groupSerializer;

	private static final String EXPECTED_SERIALIZATION
	        = "{\"name\":\"groupName\",\"gid\":7809,\"members\":[\"group\",\"member\",\"list\"]}";

	private static final String EXPECTED_SERIALIZATION_EMPTY_ATTR
	        = "{\"name\":\"groupName\",\"members\":[\"group\",\"member\",\"list\"]}";

	@Before
	public void setUp() throws Exception {
		groupSerializer = new GroupSerializer();
	}

	@Test
	public void serializeShouldSucceed() throws IOException {
		Group group = new Group();
		group.putAttribute(GroupField.GroupName, "groupName");
		group.putAttribute(GroupField.GroupID, "7809");
		group.putAttribute(GroupField.GroupList, "group");
		group.putAttribute(GroupField.GroupList, "member");
		group.putAttribute(GroupField.GroupList, "list");
		JsonFactory factory = new JsonFactory();

		StringWriter stringWriter = new StringWriter();
		JsonGenerator generator = factory.createGenerator(stringWriter);
		groupSerializer.serialize(group, generator, null);
		generator.close();
		assertEquals("failed to serial group.", EXPECTED_SERIALIZATION, stringWriter.toString());
	}

	@Test
	public void serializeWithEmptyAttrShouldSucceed() throws IOException {
		Group group = new Group();
		group.putAttribute(GroupField.GroupName, "groupName");
		group.putAttribute(GroupField.GroupList, "group");
		group.putAttribute(GroupField.GroupList, "member");
		group.putAttribute(GroupField.GroupList, "list");
		JsonFactory factory = new JsonFactory();

		StringWriter stringWriter = new StringWriter();
		JsonGenerator generator = factory.createGenerator(stringWriter);
		groupSerializer.serialize(group, generator, null);
		generator.close();
		assertEquals("failed to serial group.", EXPECTED_SERIALIZATION_EMPTY_ATTR, stringWriter.toString());
	}

}
