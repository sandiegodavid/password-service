package com.dcai.passwordService.mapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import com.dcai.passwordService.model.AttributeType;
import com.fasterxml.jackson.core.JsonGenerator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SerializerHelper {

	public static void serialField(JsonGenerator generator, String field, AttributeType attrType, Object attr)
	        throws IOException {
		if (attr != null) {
			switch (attrType) {
			case BigDecimal: {
				generator.writeNumberField(field, (BigDecimal) attr);
				break;
			}
			case Double: {
				generator.writeNumberField(field, (Double) attr);
				break;
			}
			case Float: {
				generator.writeNumberField(field, (Float) attr);
				break;
			}
			case Integer: {
				generator.writeNumberField(field, (Integer) attr);
				break;
			}
			case Long: {
				generator.writeNumberField(field, (Long) attr);
				break;
			}
			case String: {
				generator.writeStringField(field, (String) attr);
				break;
			}
			case StringList: {
				List<String> listAttr = (List<String>) attr;
				generator.writeArrayFieldStart(field);
				listAttr.forEach(t -> {
					try {
						generator.writeString(t);
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				});
				generator.writeEndArray();
				break;
			}
			default: {
				log.error("attribute is either String or Number: {}:{}", field, attr);
				throw new IOException("bad attribute: " + field + ":" + attr);
			}
			}
		}
	}
}
