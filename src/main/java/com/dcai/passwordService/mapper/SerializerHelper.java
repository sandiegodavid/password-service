package com.dcai.passwordService.mapper;

import java.io.IOException;
import java.math.BigDecimal;

import com.fasterxml.jackson.core.JsonGenerator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SerializerHelper {

	public static void serialField(JsonGenerator generator, String field, Object attr) throws IOException {
		if (attr != null) {
			if (attr instanceof BigDecimal) {
				generator.writeNumberField(field, (BigDecimal) attr);
			} else if (attr instanceof Double) {
				generator.writeNumberField(field, (Double) attr);
			} else if (attr instanceof Float) {
				generator.writeNumberField(field, (Float) attr);
			} else if (attr instanceof Integer) {
				generator.writeNumberField(field, (Integer) attr);
			} else if (attr instanceof Long) {
				generator.writeNumberField(field, (Long) attr);
			} else if (attr instanceof String) {
				generator.writeStringField(field, (String) attr);
			} else {
				log.error("attribute is either String or Number: {}:{}", field, attr);
				throw new IOException("bad attribute: " + field + ":" + attr);
			}
		}
	}

}
