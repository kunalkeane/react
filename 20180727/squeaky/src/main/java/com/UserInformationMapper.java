package com;

import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.support.AbstractContextMapper;




public class UserInformationMapper extends AbstractContextMapper<UserInformation> {

	private static final String USERNAME = "cn";
	private static final String FIRSTNAME = "givenName";
	private static final String LASTNAME = "sn";
	private static final String GLOBALID = "capgemini-GlobalID";
	private static final String EMAILADDRESS = "mail";
	private static final String DESIGNATION = "title";
	private static final String DEPARTMENT = "department";
	private static final String DISPLAYNAME = "displayName";
	private static final String EMPLOYEETYPE = "employeeType";
	private static final String MOBILE = "mobile";
	private static final String ADDRESSCOMPANY = "company";
	private static final String ADDRESSSTREET = "streetAddress";
	private static final String ADDRESSCITY = "l";
	private static final String ADDRESSSTATE = "st";
	
	
	@Override
	protected UserInformation doMapFromContext(DirContextOperations context) {
		UserInformation user = new UserInformation();
		user.setUsername(context.getStringAttribute(USERNAME));
		user.setFirstName(context.getStringAttribute(FIRSTNAME));
		user.setLastName(context.getStringAttribute(LASTNAME));
		user.setGlobalId(context.getStringAttribute(GLOBALID));
		user.setEmailAddress(context.getStringAttribute(EMAILADDRESS));
		user.setDesignation(context.getStringAttribute(DESIGNATION));
		user.setDepartment(context.getStringAttribute(DEPARTMENT));
		user.setDisplayName(context.getStringAttribute(DISPLAYNAME));
		user.setEmployeeType(context.getStringAttribute(EMPLOYEETYPE));
		user.setMobile(context.getStringAttribute(MOBILE));
		user.setAddressCompany(context.getStringAttribute(ADDRESSCOMPANY));
		user.setAddressStreet(context.getStringAttribute(ADDRESSSTREET));
		user.setAddressCity(context.getStringAttribute(ADDRESSCITY));
		user.setAddressState(context.getStringAttribute(ADDRESSSTATE));
		return user;
	}
}