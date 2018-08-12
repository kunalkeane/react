package com.notification.service;

import java.util.logging.Logger;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
public class EmailJob extends QuartzJobBean {
	
	private static final Logger LOGGER = Logger.getLogger(EmailJob.class.getName());
	
	private EmailTask emailTask;
	
	@Override
	protected void executeInternal(JobExecutionContext ctx) throws JobExecutionException {
		LOGGER.info("Starting EmailJob ...");
		try {
			emailTask.task();
		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
		LOGGER.info("Finishing CGTrainingNonCompliantJob ...");
	}

	public EmailTask getEmailTask() {
		return emailTask;
	}


	public void setEmailTask(EmailTask emailTask) {
		this.emailTask = emailTask;
	}
	
}
