package com.dcai.password.service.mapper;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.dcai.password.service.model.AttributeType;
import com.dcai.password.service.model.Group;
import com.dcai.password.service.model.GroupField;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

@Component
public class GroupSerializer extends StdSerializer<Group> {

	protected GroupSerializer() {
		super(Group.class);
	}

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public void serialize(Group group, JsonGenerator generator, SerializerProvider provider) throws IOException {
		generator.writeStartObject();
		for (GroupField field : GroupField.values()) {
			Object attr = group.getAttribute(field);
			if (attr != null && field.getAttributeType() != AttributeType.StringList) {
				attr = ((List<Object>) attr).get(0);
			}
			SerializerHelper.serialField(generator, field.getField(), field.getAttributeType(), attr);
		}
		generator.writeEndObject();
	}
}
