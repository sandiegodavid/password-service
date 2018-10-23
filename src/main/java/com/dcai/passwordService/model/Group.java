package com.dcai.passwordService.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class Group implements AttributeCollection<GroupField> {

	private final static String LIST_ATTRIBUITE_DELIMITER = ",";

	private MultiValueMap<GroupField, Object> attributes = new LinkedMultiValueMap<>();

	@Override
	public void putAttribute(GroupField field, String value) {
		switch (field.getAttributeType()) {
		case Integer: {
			attributes.add(field, Integer.parseInt(value));
			break;
		}
		case StringList: {
			if (value.length() == 0) {
				attributes.addAll(field, Collections.emptyList());
			} else {
				String[] splitted = value.split(LIST_ATTRIBUITE_DELIMITER);
				attributes.addAll(field, Arrays.asList(splitted));
			}
			break;
		}
		default: {
			attributes.add(field, value);
		}
		}
	}

	@Override
	public List<Object> getAttribute(GroupField field) {
		return attributes.get(field);
	}
}
