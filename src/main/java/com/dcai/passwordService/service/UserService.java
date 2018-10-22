package com.dcai.passwordService.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcai.passwordService.model.User;
import com.dcai.passwordService.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public List<User> getAllUsers() {
		return userRepository.getAllUsers();
	}

	public List<User> getUsers(Map<String, String> query) {
		return userRepository.findUsers(query);
	}

}
