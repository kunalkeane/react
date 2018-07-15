package com;

import java.security.Principal;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.service.UserService;



@RestController
public class UserResource {
	
	private static final Logger LOGGER = Logger.getLogger(UserResource.class.getName());

	@Autowired
	private UserService userService;
	
	
	@RequestMapping(value = "/loggedInUser", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public User currentlyLoggedInUser(HttpServletRequest request)	{
		LOGGER.info("Inside currentlyLoggedInUser..");
		User user = null;
		try	{
			return queryUserByDomainLoggin(getUserDomainName(request));
		}catch(RuntimeException exception)	{
			throw new RuntimeException(exception);
		}
	}

	private User queryUserByDomainLoggin(final String doaminUsername) {
		if(doaminUsername != null && !"".equals(doaminUsername))	{
			return userService.queryByUsername(doaminUsername);
		}
		throw new RuntimeException("Could not retrieve User by DomainLoggin");
	}
	
	private String getUserDomainName(final HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		if(principal != null)	{
			return principal.getName();
		}
		throw new RuntimeException("Could not get Principal Object");
	}
}
