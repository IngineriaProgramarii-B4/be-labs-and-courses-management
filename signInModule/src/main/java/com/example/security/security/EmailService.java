package com.example.security.security;

import com.example.security.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    public void sendPasswordResetEmail(UserEntity user, String resetToken) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply_b4@gmail.com");

        message.setTo(user.getEmail());

        String resetLink = "www.google.com";
        String emailBody = "Reset your password using the following link:\n" + resetLink + "\n\nThank you";

        message.setText(emailBody);
        message.setSubject("Reset Password");
        mailSender.send(message);


    }

}
