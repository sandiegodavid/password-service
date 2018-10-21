package com.dcai.passwordService.model;

import lombok.Data;

@Data
public class Group {
	private String name;
	private String password;
	private String id;
	private String members;
}
