package com.dcai.passwordService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dcai.passwordService.model.User;
import com.dcai.passwordService.service.UserService;

@RestController("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public List<User> getUsers() {
		// TODO: map to object with non enum name fields
		return userService.getAllUsers();
	}

}
