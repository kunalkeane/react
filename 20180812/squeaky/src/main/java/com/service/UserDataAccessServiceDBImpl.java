package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.User;
import com.dao.CommentDAO;


@Component
public class UserDataAccessServiceDBImpl implements UserDataAccess {

	@Autowired
	private CommentDAO commentDao;
	
	@Cacheable(value=CachingConfig.CACHE_USER, key="#userId")
	@Override
	public User getUserByUserId(String userId) {
		User user = commentDao.getUser(userId);
		if(user == null){
			user = new User();
			user.setUserName(userId);
		}
		return user;
	}
	
	@Override
	public String getUserNameByUserId(String userId) {
		User user = getUserByUserId(userId);
		if(user != null && user.getFirstName() != null){
			return user.getFirstName() +" "+user.getLastName();	
		}
		return userId;
	}
}
