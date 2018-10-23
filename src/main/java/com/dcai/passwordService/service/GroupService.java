package com.dcai.passwordService.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.dcai.passwordService.model.Group;
import com.dcai.passwordService.repository.GroupRepository;

@Service
public class GroupService {

	@Autowired
	private GroupRepository groupRepository;

	public List<Group> getAllGroups() {
		return groupRepository.getAllGroups();
	}

	public List<Group> getGroups(MultiValueMap<String, String> query) {
		return groupRepository.findGroups(query);
	}

	public Optional<Group> getGroup(Integer gid) {
		MultiValueMap<String, String> query = new LinkedMultiValueMap<>();
		query.add("gid", String.valueOf(gid));
		List<Group> groups = groupRepository.findGroups(query);
		return groups.isEmpty() ? Optional.empty() : Optional.of(groups.get(0));
	}

}
