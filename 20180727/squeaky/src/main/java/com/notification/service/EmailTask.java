package com.notification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("emailTask")
public class EmailTask {
	
	@Autowired
	private EmailService emailService;
	
	
	public void task() {
		emailService.sendEmail();
	}

}
