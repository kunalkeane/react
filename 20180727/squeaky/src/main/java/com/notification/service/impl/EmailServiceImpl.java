package com.notification.service.impl;

import java.io.StringWriter;
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.dao.EmailDAO;
import com.notification.service.EmailService;

@Component
public class EmailServiceImpl implements EmailService {

	private static final Logger LOGGER = Logger.getLogger(EmailServiceImpl.class.getName());

	@Autowired
	private JavaMailSenderImpl mailSender;
	@Autowired
	private VelocityEngine velocityEngine;
	@Autowired
	EmailDAO emailDao;

	
	@Override
	public void sendEmail() {
		LOGGER.info("inside sendCGTrainingNonCompliantMail");
		
		if(emailDao.getCommentsInXHours(4) == null){
			LOGGER.info("no new activities in past 4 hours");
			return;
		}
		
		Template template = velocityEngine.getTemplate("template/MailFormat.vm");
		
		VelocityContext vc = new VelocityContext();
		vc.put("comments", emailDao.getCommentsInXHours(4));
		StringWriter writer = new StringWriter();
		template.merge(vc, writer);

		
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper;
			try {
				helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
						
			mimeMessage.setContent(writer.toString(), "text/html");
			String to[] = emailDao.getEmails();
			helper.setTo(to);
			helper.setSubject("Squeaky");
			helper.setFrom("tagpune.fssbu@capgemini.com");
			mailSender.send(mimeMessage);
			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}

	}
	

	
}
