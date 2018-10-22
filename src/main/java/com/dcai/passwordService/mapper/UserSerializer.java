package com.dcai.passwordService.mapper;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.dcai.passwordService.model.PasswdField;
import com.dcai.passwordService.model.User;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

@Component
public class UserSerializer extends StdSerializer<User> {

	protected UserSerializer() {
		super(User.class);
	}

	private static final long serialVersionUID = 1L;

	@Override
	public void serialize(User user, JsonGenerator generator, SerializerProvider provider) throws IOException {
		generator.writeStartObject();
		for (PasswdField field : PasswdField.values()) {
			SerializerHelper.serialField(generator, field.getField(), user.getAttribute(field));
		}
		generator.writeEndObject();
	}
}
