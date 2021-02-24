package com.nagarro.notificationservice.consumers;

import java.util.Arrays;
import java.util.Map;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.stereotype.Component;

import com.nagarro.notificationservice.constants.CommonConstants;
import com.nagarro.notificationservice.constants.EmailConstants;
import com.nagarro.notificationservice.dto.MailProperties;
import com.nagarro.notificationservice.enums.OrderStatus;
import com.nagarro.notificationservice.services.EmailService;

@Component
public class EmailSendConsumer {

	private static final Logger LOG = LoggerFactory.getLogger(EmailSendConsumer.class);

	@Value("${credential.available:false}")
	private boolean isCredentialAvailable;

	@Autowired
	private EmailService emailService;

	@Value("${spring.mail.username}")
	private String from;

	@JmsListener(destination = CommonConstants.ORDER_MANAGEMENT_QUEUE_NAME)
	public void sendMail(Map<String, Object> orderInfoMap) throws MessageConversionException, JMSException {
		LOG.debug("Inside sendMail method");

		MailProperties mailProperties = new MailProperties();
		mailProperties.setFrom(from);
		mailProperties.setContentPlaceholders(orderInfoMap);
		if (orderInfoMap.get("orderStatus").equals(OrderStatus.UNDER_PRODUCER_APPROVAL.name())) {
			mailProperties.setMailTo(new String[] { (String) orderInfoMap.get("producerEmail") });
			mailProperties.setSubject(EmailConstants.PRODUCER_SUBJECT);

		} else {

			mailProperties.setMailTo(new String[] { (String) orderInfoMap.get("consumerEmail"),
					(String) orderInfoMap.get("producerEmail") });
			mailProperties.setSubject(EmailConstants.ORDER_DECISION_SUBJECT);

		}

		if (isCredentialAvailable) {
			emailService.sendEmail(mailProperties);
		} else {
			LOG.debug("No Credentials available.");
		}
		emailService.addRecipentToSentMailList(Arrays.asList(mailProperties.getMailTo()));
	}

}
