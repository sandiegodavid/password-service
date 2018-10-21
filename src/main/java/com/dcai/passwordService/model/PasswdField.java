package com.dcai.passwordService.model;

import lombok.Getter;

public enum PasswdField {
	UserName("name"),
	Password("password"),
	UserID("uid"),
	GroupID("gid"),
	UserInfo("comment"),
	HomeDirectory("home"),
	CommandShell("shell");

	@Getter
	private String field;

	PasswdField(String field) {
		this.field = field;
	}
}
