package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.User;
import com.UserInformation;

@Component
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDataAccess ldapDataAccessObject;

	@Override
	public User queryByUsername(String username) {
		UserInformation userInfo = null;
		userInfo = ldapDataAccessObject.queryByUsername(username);
		return transformUserDto(userInfo);
	}


	
	private User transformUserDto(UserInformation userInfo) {
		User user = new User();
		user.setUsername(userInfo.getUsername());
		user.setFirstName(userInfo.getFirstName());
		user.setLastName(userInfo.getLastName());
		user.setGlobalId(userInfo.getGlobalId());
		user.setEmailAddress(userInfo.getEmailAddress());
		user.setDesignation(userInfo.getDesignation());
		user.setDepartment(userInfo.getDepartment());
		user.setDisplayName(userInfo.getDisplayName());
		user.setEmployeeType(userInfo.getEmployeeType());
		user.setMobile(userInfo.getMobile());
		user.setAddressCompany(userInfo.getAddressCompany());
		user.setAddressStreet(userInfo.getAddressStreet());
		user.setAddressCity(userInfo.getAddressCity());
		user.setAddressState(userInfo.getAddressState());
		return user;
	}
}
