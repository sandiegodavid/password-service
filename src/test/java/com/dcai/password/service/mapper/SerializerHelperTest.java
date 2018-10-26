package com.dcai.password.service.mapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyString;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import com.dcai.password.service.model.AttributeType;
import com.fasterxml.jackson.core.JsonGenerator;

public class SerializerHelperTest {

	private JsonGenerator generator;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setUp() {
		generator = Mockito.mock(JsonGenerator.class);
	}

	@Test
	public void serialFieldShouldWriteBigDecimal() throws IOException {
		SerializerHelper.serialField(generator, "field", AttributeType.BigDecimal, BigDecimal.ONE);
		Mockito.verify(generator).writeNumberField("field", BigDecimal.ONE);
	}

	@Test
	public void serialFieldShouldWriteDouble() throws IOException {
		SerializerHelper.serialField(generator, "field", AttributeType.Double, Double.MAX_VALUE);
		Mockito.verify(generator).writeNumberField("field", Double.MAX_VALUE);
	}

	@Test
	public void serialFieldShouldWriteFloat() throws IOException {
		SerializerHelper.serialField(generator, "field", AttributeType.Float, Float.MAX_VALUE);
		Mockito.verify(generator).writeNumberField("field", Float.MAX_VALUE);
	}

	@Test
	public void serialFieldShouldWriteInteger() throws IOException {
		SerializerHelper.serialField(generator, "field", AttributeType.Integer, Integer.MAX_VALUE);
		Mockito.verify(generator).writeNumberField("field", Integer.MAX_VALUE);
	}

	@Test
	public void serialFieldShouldWriteLong() throws IOException {
		SerializerHelper.serialField(generator, "field", AttributeType.Long, Long.MAX_VALUE);
		Mockito.verify(generator).writeNumberField("field", Long.MAX_VALUE);
	}

	@Test
	public void serialFieldShouldWriteString() throws IOException {
		SerializerHelper.serialField(generator, "field", AttributeType.String, "String");
		Mockito.verify(generator).writeStringField("field", "String");
	}

	@Test
	public void serialFieldShouldWriteStringList() throws IOException {
		SerializerHelper.serialField(generator, "field", AttributeType.StringList, Arrays.asList("String", "List"));
		Mockito.verify(generator).writeString("String");
	}

	@Test
	public void serialStringListFieldShouldThrow() throws IOException {
		thrown.expect(RuntimeException.class);
		Mockito.doThrow(new IOException()).when(generator).writeString(anyString());
		SerializerHelper.serialField(generator, "field", AttributeType.StringList, Arrays.asList("String", "List"));
		Mockito.verify(generator).writeString("String");
	}

	@Test
	public void serialFieldShouldThrowException() throws IOException {
		thrown.expect(IOException.class);
		SerializerHelper.serialField(generator, "field", AttributeType.Undefined, Collections.EMPTY_MAP);
	}
}
