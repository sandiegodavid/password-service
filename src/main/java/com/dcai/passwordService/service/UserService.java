package com.dcai.passwordService.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

	public Optional<User> getUser(Integer uid) {
		Map<String, String> query = new HashMap<>();
		query.put("uid", String.valueOf(uid));
		List<User> users = userRepository.findUsers(query);
		return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
	}

}
