package com.dcai.passwordService.mapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

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
		SerializerHelper.serialField(generator, "field", BigDecimal.ONE);
		Mockito.verify(generator).writeNumberField("field", BigDecimal.ONE);
	}

	@Test
	public void serialFieldShouldWriteDouble() throws IOException {
		SerializerHelper.serialField(generator, "field", Double.MAX_VALUE);
		Mockito.verify(generator).writeNumberField("field", Double.MAX_VALUE);
	}

	@Test
	public void serialFieldShouldWriteFloat() throws IOException {
		SerializerHelper.serialField(generator, "field", Float.MAX_VALUE);
		Mockito.verify(generator).writeNumberField("field", Float.MAX_VALUE);
	}

	@Test
	public void serialFieldShouldWriteInteger() throws IOException {
		SerializerHelper.serialField(generator, "field", Integer.MAX_VALUE);
		Mockito.verify(generator).writeNumberField("field", Integer.MAX_VALUE);
	}

	@Test
	public void serialFieldShouldWriteLong() throws IOException {
		SerializerHelper.serialField(generator, "field", Long.MAX_VALUE);
		Mockito.verify(generator).writeNumberField("field", Long.MAX_VALUE);
	}

	@Test
	public void serialFieldShouldWriteString() throws IOException {
		SerializerHelper.serialField(generator, "field", "String");
		Mockito.verify(generator).writeStringField("field", "String");
	}

	@Test
	public void serialFieldShouldThrowException() throws IOException {
		thrown.expect(IOException.class);
		SerializerHelper.serialField(generator, "field", Collections.EMPTY_MAP);
	}
}
