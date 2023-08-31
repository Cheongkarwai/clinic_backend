package com.cheong.clinic_api.utility.service;

import jakarta.mail.MessagingException;

public interface IEmailService {

	boolean send(String message,String to, String type) throws MessagingException;
	
	void sendHtmlEmail(String to,String subject,String content) throws MessagingException;
}
