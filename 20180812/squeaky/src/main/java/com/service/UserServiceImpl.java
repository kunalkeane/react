package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.User;

@Component
public class UserServiceImpl implements UserService {

	@Autowired
	@Qualifier(value="userDataAccessServiceDBImpl")
	private UserDataAccess userDataAccessObject;
	
	@Override
	public User queryByUsername(String username) {
		User user = null;
		user = userDataAccessObject.getUserByUserId(username);
		return user;
	}


	
	}
