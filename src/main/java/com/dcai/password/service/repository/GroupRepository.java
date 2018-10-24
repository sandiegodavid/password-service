package com.dcai.password.service.repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;

import com.dcai.password.service.exception.FileError;
import com.dcai.password.service.model.GroupField;
import com.dcai.password.service.model.Group;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class GroupRepository {
	static final String DEFAULT_GROUPS_FILE = "/etc/groups";
	static final String DEFAULT_FIELD_DELIMITER = ":";
	static final String DEFAULT_COMMENT_MARKER = "#";

	@Value("${file.groups:" + DEFAULT_GROUPS_FILE + "}")
	private String groupsFile;

	@Value("${file.groups.field.delimiter:" + DEFAULT_FIELD_DELIMITER + "}")
	private char fieldDelimiter;

	@Value("${file.groups.commentMarker:" + DEFAULT_COMMENT_MARKER + "}")
	private char commentMarker;

	private CSVFormat csvFormat;

	@PostConstruct
	public void init() {
		csvFormat = CSVFormat.DEFAULT.withDelimiter(fieldDelimiter).withCommentMarker(commentMarker)
		        .withHeader(GroupField.class);
		log.debug("groupsFile:({}), csvFormat:{}", groupsFile, csvFormat);
	}

	public List<Group> getAllGroups() {
		return findGroups(null);
	}

	public List<Group> findGroups(MultiValueMap<String, String> query) {
		consolidateQueryAliases(query);
		List<Group> groups = new ArrayList<>();
		try (Reader reader = new BufferedReader(new FileReader(groupsFile))) {
			CSVParser parser = csvFormat.parse(reader);
			for (CSVRecord record : parser) {
				Group group = new Group();
				for (GroupField field : GroupField.values()) {
					if (GroupField.Password == field) {
						// don't read password
						continue;
					}
					group.putAttribute(field, record.get(field));
				}
				if (selectGroup(group, query)) {
					groups.add(group);
				}
			}
		} catch (IOException e) {
			log.error("Groups file:({}) not found or can't be parsed.", groupsFile, e);
			throw new FileError();
		}
		log.debug("returning {} groups.", groups.size());
		return groups;
	}

	private void consolidateQueryAliases(MultiValueMap<String, String> query) {
		for (GroupField f : GroupField.values()) {
			f.getAliases().forEach(a -> {
				if (query == null) {
					return;
				}
				List<String> aliasQuery = query.remove(a);
				if (aliasQuery == null) {
					return;
				}
				query.merge(f.getField(), aliasQuery, (list1, list2) -> Stream.of(list1, list2)
				        .flatMap(Collection::stream)
				        .collect(Collectors.toList()));
				;
			});
		}
	}

	static boolean selectGroup(Group group, MultiValueMap<String, String> query) {
		if (query == null || query.isEmpty()) {
			return true;
		}
		for (GroupField f : GroupField.values()) {
			for (Map.Entry<String, List<String>> e : query.entrySet()) {
				if (e.getKey().contentEquals(f.getField())) {
					List<String> queryValue = e.getValue();
					if (queryValue == null) {
						break;
					}
					queryValue = queryValue.stream().filter(q -> q != null).collect(Collectors.toList());
					if (queryValue.isEmpty()) {
						break;
					}
					List<Object> attrList = group.getAttribute(f);
					if (attrList == null) {
						return false;
					}
					List<String> attr = new ArrayList<>();
					attrList.forEach(o -> {
						if (o != null) {
							attr.add(o.toString());
						}
					});
					if (!attr.containsAll(queryValue)) {
						return false;
					}
					break;
				}
			}
		}
		return true;
	}
}
