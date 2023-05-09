package com.example.security;


import com.example.security.model.UserEntity;
import com.example.security.security.EmailService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.io.UnsupportedEncodingException;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
 class EmailServiceTest {

    @InjectMocks
    private EmailService emailService;

    @Mock
    private JavaMailSender mailSender;

    private UserEntity user;
    private String resetToken;

    @BeforeEach
    public void setUp() {
        user = new UserEntity();
        user.setEmail("test@example.com");

        resetToken = "sampleResetToken";
    }

    @Test
     void testSendPasswordResetEmail() throws MessagingException, UnsupportedEncodingException {
        emailService.sendPasswordResetEmail(user, resetToken);

        Mockito.verify(mailSender, Mockito.times(1)).send(any(SimpleMailMessage.class));
    }
}

