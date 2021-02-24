package com.nagarro.notificationservice.services;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.nagarro.notificationservice.dto.MailProperties;

public interface EmailService {
	
	void sendEmail(MailProperties mailProperties);
	
	Map<Date,List<String>> getAllSentEmails();

	void addRecipentToSentMailList(List<String> sendTo);

}
