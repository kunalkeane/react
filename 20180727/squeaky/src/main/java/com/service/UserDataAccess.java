package com.service;

import com.UserInformation;

public interface UserDataAccess {
	public UserInformation queryByUsername(String username);
	public String getUserNameByUserId(String userId);
}
