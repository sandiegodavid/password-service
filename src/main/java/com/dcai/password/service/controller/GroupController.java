package com.dcai.password.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dcai.password.service.exception.RecordMissingError;
import com.dcai.password.service.model.Group;
import com.dcai.password.service.service.GroupService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/groups")
public class GroupController {

	@Autowired
	private GroupService groupService;

	@GetMapping
	public List<Group> getAllGroups() {
		log.debug("getAllGroups is called.");
		return groupService.getAllGroups();
	}

	@GetMapping("/query")
	public List<Group> getGroups(@RequestParam MultiValueMap<String, String> query) {
		log.debug("query is called. RequestParam:{}", query);
		return groupService.getGroups(query);
	}

	@GetMapping("/{gid}")
	public Group getGroup(@PathVariable Integer gid) {
		log.debug("getGroup is called. gid:{}", gid);
		return groupService.getGroup(gid).orElseThrow(RecordMissingError::new);
	}

}
