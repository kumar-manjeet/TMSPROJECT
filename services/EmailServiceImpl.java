package com.tms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender emailSender;

	@Override
	public void sendSimpleMessage(String to, String name, String password) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject("Regarding password reset");
		message.setText("Hii " + name + " this is your email " + to + " and this your password " + password
				+ " please reset your password!!\n"
				+ "click this link to reset password http://localhost:9000/login/resetPassword");
		emailSender.send(message);
	}

}
