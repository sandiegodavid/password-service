package com.dcai.passwordService.model;

import lombok.Getter;

@Getter
public enum PasswdField {
	UserName("name", AttributeType.String),
	Password("password", AttributeType.String),
	UserID("uid", AttributeType.Integer),
	GroupID("gid", AttributeType.Integer),
	UserInfo("comment", AttributeType.String),
	HomeDirectory("home", AttributeType.String),
	CommandShell("shell", AttributeType.String);

	private String field;

	// unfortunately enum can't be generic
	private AttributeType attributeType;

	PasswdField(String field, AttributeType attributeType) {
		this.field = field;
		this.attributeType = attributeType;
	}
}