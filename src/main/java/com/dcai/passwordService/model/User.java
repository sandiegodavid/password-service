package com.dcai.passwordService.model;

import java.util.HashMap;
import java.util.Map;

public class User implements AttributeCollection<PasswdField> {
	private Map<PasswdField, Object> attributes = new HashMap<>();

	@Override
	public void putAttribute(PasswdField field, String value) {
		if (field.getAttributeType() == AttributeType.Integer) {
			attributes.put(field, Integer.parseInt(value));
		} else {
			attributes.put(field, value);
		}
	}

	@Override
	public Object getAttribute(PasswdField field) {
		return attributes.get(field);
	}
}
