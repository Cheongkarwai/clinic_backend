package com.cheong.clinic_api.utility.service;

import java.util.NoSuchElementException;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.cheong.clinic_api.auth.domain.User;
import com.cheong.clinic_api.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService implements IEmailService {

	private final JavaMailSender javaMailSender;
	private final UserRepository userRepository;
	private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

	@Override
	public boolean send(String message, String to, String type) throws MessagingException {

		boolean isSend = false;

		switch (type) {
		case "forgot-password":
			sendForgotPasswordEmail(to);
			isSend = true;
			break;
		}

		return isSend;

	}
	
	public void sendHtmlEmail(String to,String subject,String content) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,false);
		mimeMessageHelper.setSubject(subject);
		mimeMessageHelper.setTo(to);
		mimeMessageHelper.setText(content, true);
		javaMailSender.send(mimeMessage);
	}

	private void sendForgotPasswordEmail(String to) throws MessagingException {

		User user = userRepository.findByUserProfile_email(to).orElseThrow(()->new NoSuchElementException("User not found"));
		
		String changePasswordPage = "http://localhost:4200/change-password";
		
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false);
		mimeMessageHelper.setSubject("Forgot Password");
		mimeMessageHelper.setTo(to);
		mimeMessageHelper.setText(
				"<h5>Click <a href='"+changePasswordPage+"?username="+user.getId()+"'>here</a> to change password</h5>", true);

		javaMailSender.send(mimeMessage);
	}

}
