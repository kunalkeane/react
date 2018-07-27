package com.service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.stereotype.Component;

import com.UserInformation;
import com.UserInformationMapper;


@Component
public class UserDataAccessServiceLdapImpl implements UserDataAccess {

	private static final String METHOD_NOT_IMPL_MESSAGE = "Method Not Implemented";
	private static final String USERNAME_QUERY_PARAM = "cn";
	private static final String EMAIL_QUERY_PARAM = "mail";
	private static final String CACHE_MANAGER = "cacheManager";
	private static final String CACHE_USER = "users";
	private static final String CACHE_MAIL = "mails";

	private static final Logger LOGGER = Logger.getLogger(UserDataAccessServiceLdapImpl.class.getName());
	
	@Autowired
	private LdapTemplate ldapTemplate;
	
	@Cacheable(value=CACHE_USER, key="#username")
	@Override
	public UserInformation queryByUsername(String username) {
		UserInformation user = getUserInformation(generateQueryByUsername(username));
		if(user == null){
			user = new UserInformation();
			user.setUsername(username);
		}
		return user;
	}
	
	@Override
	public String getUserNameByUserId(String userId) {
		UserInformation userInformation = queryByUsername(userId);
		if(userInformation != null && userInformation.getFirstName() != null){
			return userInformation.getFirstName() +" "+userInformation.getLastName();	
		}
		return userId;
	}

	
	private UserInformation getUserInformation(LdapQuery query) {
		UserInformation user = null;
		List<UserInformation> users = ldapTemplate.search(query,new UserInformationMapper());
		
		if(users != null && users.size() == 1)	{
			
			user = users.get(0);
			
		}	
		return user;
	}
	
	private LdapQuery generateQueryByUsername(String username) {
	    return LdapQueryBuilder.query().where(USERNAME_QUERY_PARAM).is(username);
	}

}
