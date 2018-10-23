package com.dcai.passwordService.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dcai.passwordService.exception.RecordMissingError;
import com.dcai.passwordService.model.Group;
import com.dcai.passwordService.model.User;
import com.dcai.passwordService.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public List<User> getAllUsers() {
		log.debug("getAllUsers is called.");
		return userService.getAllUsers();
	}

	@GetMapping("/query")
	public List<User> getUsers(@RequestParam Map<String, String> query) {
		log.debug("query is called. RequestParam:{}", query);
		return userService.getUsers(query);
	}

	@GetMapping("/{uid}")
	public User getUser(@PathVariable Integer uid) {
		log.debug("getUser is called. uid:{}", uid);
		return userService.getUser(uid).orElseThrow(RecordMissingError::new);
	}

	@GetMapping("/{uid}/groups")
	public List<Group> getUserGroups(@PathVariable Integer uid) {
		log.debug("getUserGroups is called. uid:{}", uid);
		return userService.getUserGroups(uid);
	}

}
