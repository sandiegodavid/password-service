package com.dcai.password.service.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcai.password.service.exception.RecordMissingError;
import com.dcai.password.service.model.Group;
import com.dcai.password.service.model.User;
import com.dcai.password.service.repository.UserRepository;

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

	public List<Group> getUserGroups(Integer uid) {
		User user = getUser(uid).orElseThrow(RecordMissingError::new);
		return userRepository.findUserGroups(user);
	}

}
