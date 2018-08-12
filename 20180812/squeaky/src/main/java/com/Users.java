package com;

import java.util.List;
import java.util.Set;

public class Users {
	List<User> users;
	String selectedTag;
	
	public Users(List<User> users, String selectedTag) {
		super();
		this.users = users;
		this.selectedTag = selectedTag;
	}
	public List<User> getUsers() {
		return users;
	}
	public String getSelectedTag() {
		return selectedTag;
	}
}
