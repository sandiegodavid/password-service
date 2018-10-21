package com.dcai.passwordService.model;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public class User {
	private Map<PasswdField, String> fields = new HashMap<>();
}
