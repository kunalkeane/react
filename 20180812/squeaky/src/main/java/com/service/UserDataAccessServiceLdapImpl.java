package com.service;

import org.springframework.stereotype.Component;


@Component
public class UserDataAccessServiceLdapImpl {/*
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
	
	public UserInformation queryByUsername(String username) {
		UserInformation user = getUserInformation(generateQueryByUsername(username));
		if(user == null){
			user = new UserInformation();
			user.setUsername(username);
		}
		return user;
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
	
	public static void main(String args[]){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		UserDataAccessServiceLdapImpl obj = new UserDataAccessServiceLdapImpl();
		obj.ldapTemplate = (LdapTemplate)ctx.getBean("ldapTemplate");
		System.out.println(obj.queryByUsername("kuaggarw"));
		
	}

*/}
