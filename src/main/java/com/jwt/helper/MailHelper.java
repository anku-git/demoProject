package com.jwt.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailHelper {
	@Value("${mail.subject}")
	private String subject;
	
	private final JavaMailSender javaMailSender;
	@Autowired
	public MailHelper(JavaMailSender javaMailSender) {
		this.javaMailSender=javaMailSender;
	}
	
//	public void sendMail(String recieveMail,String username,String password) {
//		 SimpleMailMessage mailMessage=new SimpleMailMessage();
//		 mailMessage.setTo(recieveMail);
//		 mailMessage.setSubject(subject);
//		 mailMessage.setText("Congratulations, your usename is: "+  username  + " password is:" + password);
//         javaMailSender.send(mailMessage);
//	}
	public void sendMail(String recieveMail,String username) {
		 SimpleMailMessage mailMessage=new SimpleMailMessage();
		 mailMessage.setTo(recieveMail);
		 mailMessage.setSubject(subject);
		 mailMessage.setText("Congratulations, your are verified  " +   username +"  now you reset your password");
        javaMailSender.send(mailMessage);
	}
	public void sendLoginMail(String recieveMail,String username) {
		 SimpleMailMessage mailMessage=new SimpleMailMessage();
		 mailMessage.setTo(recieveMail);
		 mailMessage.setSubject(subject);
		 mailMessage.setText("Congratulations, your are login sucessfully");
        javaMailSender.send(mailMessage);
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
