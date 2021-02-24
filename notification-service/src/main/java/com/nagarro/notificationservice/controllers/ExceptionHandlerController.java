package com.nagarro.notificationservice.controllers;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {
	
	private static final Logger LOG = LoggerFactory.getLogger(ExceptionHandlerController.class);
	private static final String GENERIC_ERROR_MSG = "An error has occured,Please try again after sometime.";

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleCustomException(final Exception ex, final WebRequest request) {
		LOG.error("Exception : ", ex);
		return new ResponseEntity<>(
				getErrorMap(HttpStatus.INTERNAL_SERVER_ERROR, GENERIC_ERROR_MSG),
				new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	private Map<String, Object> getErrorMap(final HttpStatus status, final String errorMsg) {
		Map<String, Object> errorMap = new HashMap<>();
		errorMap.put("errorMsg", errorMsg);
		errorMap.put("errorStatus", status.toString());
		return errorMap;
	}


}
