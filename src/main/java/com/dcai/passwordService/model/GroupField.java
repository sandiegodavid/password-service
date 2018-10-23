package com.dcai.passwordService.model;

import java.util.List;
import java.util.Collections;
import java.util.Arrays;

import lombok.Getter;

@Getter
public enum GroupField {
	GroupName("name", Collections.emptyList(), AttributeType.String),
	Password("password", Collections.emptyList(), AttributeType.String),
	GroupID("gid", Collections.emptyList(), AttributeType.Integer),
	GroupList("members", Arrays.asList("member"), AttributeType.StringList);

	private String field;

	private List<String> aliases;

	// unfortunately enum can't be generic
	private AttributeType attributeType;

	GroupField(String field, List<String> aliases, AttributeType attributeType) {
		this.field = field;
		this.aliases = aliases;
		this.attributeType = attributeType;
	}
}
