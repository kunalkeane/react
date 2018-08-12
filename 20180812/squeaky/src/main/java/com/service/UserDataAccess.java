package com.service;

import com.User;

public interface UserDataAccess {
	public User getUserByUserId(String username);
	public String getUserNameByUserId(String userId);
}
