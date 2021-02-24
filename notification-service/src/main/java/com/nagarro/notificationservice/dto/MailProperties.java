package com.nagarro.notificationservice.dto;

import java.util.Arrays;
import java.util.Map;

public class MailProperties {

	private String from;
	private String[] mailTo;
	private String subject;
	private Map<String, Object> contentPlaceholders;

	public MailProperties() {
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String[] getMailTo() {
		return mailTo;
	}

	public void setMailTo(String[] mailTo) {
		this.mailTo = mailTo;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Map<String, Object> getContentPlaceholders() {
		return contentPlaceholders;
	}

	public void setContentPlaceholders(Map<String, Object> contentPlaceholders) {
		this.contentPlaceholders = contentPlaceholders;
	}

	@Override
	public String toString() {
		return "MailProperties [from=" + from + ", mailTo=" + Arrays.toString(mailTo) + ", subject=" + subject
				+ ", contentPlaceholders=" + contentPlaceholders + "]";
	}
	

}
