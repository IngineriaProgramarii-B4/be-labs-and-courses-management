package com.example.security.security;

import com.example.security.model.UserEntity;
import com.example.security.repository.UserRepository;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public EmailService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public void sendPasswordResetEmail(UserEntity user, String resetToken) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply_b4@gmail.com");

        message.setTo(user.getEmail());

        String randomPw = randomPassword(10);
        user.setPassword(passwordEncoder.encode(randomPw));
        userRepository.save(user);

        String emailBody = "Your password has been reset.\nYour new password is   "
                + randomPw
                + "\n\nChange it as soon as you log in!!";

        message.setText(emailBody);
        message.setSubject("Reset Password");
        mailSender.send(message);

    }

    public String randomPassword(int length) {
        RandomStringGenerator pwdGenerator = new RandomStringGenerator.Builder().withinRange(97, 122)
                .build();
        return pwdGenerator.generate(length);
    }
}
