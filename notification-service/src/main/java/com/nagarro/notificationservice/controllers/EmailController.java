package com.nagarro.notificationservice.controllers;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.notificationservice.services.EmailService;

@RestController
@RequestMapping("/email")
public class EmailController {

	private static final Logger LOG = LoggerFactory.getLogger(EmailController.class);

	@Autowired
	private EmailService emailService;

	@GetMapping("/getSentEmail")
	public ResponseEntity<Map<Date, List<String>>> getAllSentMails() {
		LOG.debug("Inside getTotalEmail method");
		return new ResponseEntity<>(emailService.getAllSentEmails(), HttpStatus.OK);

	}
}
