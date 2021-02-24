package com.nagarro.notificationservice.services.impl;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.nagarro.notificationservice.constants.CommonConstants;
import com.nagarro.notificationservice.dto.MailProperties;
import com.nagarro.notificationservice.enums.OrderStatus;
import com.nagarro.notificationservice.services.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

	private static final Logger LOG = LoggerFactory.getLogger(EmailServiceImpl.class);

	private Map<Date, List<String>> allSentEmails = new HashMap<>();

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private SpringTemplateEngine templateEngine;

	public void sendEmail(MailProperties mailProperties) {
		LOG.debug("Inside sendEmail method Email Properties:{}", mailProperties);
		MimeMessage message = emailSender.createMimeMessage();
		String orderStatus = (String) mailProperties.getContentPlaceholders().get("orderStatus");
		String templateName = "";
		templateName = orderStatus.equals(OrderStatus.UNDER_PRODUCER_APPROVAL.name())
				? CommonConstants.APPROVER_TEMPLATE
				: orderStatus.equals(OrderStatus.CANCEL.name()) ? CommonConstants.ORDER_CANCEL_TEMPLATE
						: CommonConstants.ORDER_APPROVED_TEMPLATE;
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
			Context context = new Context();
			context.setVariables(mailProperties.getContentPlaceholders());

			String html = templateEngine.process(templateName, context);
			helper.setTo(mailProperties.getMailTo());
			helper.setText(html, true);
			helper.setSubject(mailProperties.getSubject());
			helper.setFrom(mailProperties.getFrom());
			emailSender.send(message);
			LOG.info("E-mail Sent Successfully to:{}", Arrays.toString(mailProperties.getMailTo()));
		} catch (MessagingException e) {
			LOG.debug("Error occured while sending mail", e);
		}
	}

	@Override
	public Map<Date, List<String>> getAllSentEmails() {
		return allSentEmails;
	}

	@Override
	public void addRecipentToSentMailList(List<String> sendTo) {
		allSentEmails.put(new Date(), sendTo);
	}

}
