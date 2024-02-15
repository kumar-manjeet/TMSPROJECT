package com.tms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;

@Service
public class EmailSendService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	
	public void toSendEmail(String to, String subject, String message) throws MessagingException{
		try {
			SimpleMailMessage mail = new SimpleMailMessage();
		
		 mail.setTo(to);
		 mail.setSubject(subject);
		 mail.setText(message);

	      mailSender.send(mail);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
