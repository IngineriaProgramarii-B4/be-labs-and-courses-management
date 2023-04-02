package com.example.ProiectIP.service;
import com.example.ProiectIP.dao.UserRepository;
import com.example.ProiectIP.model.Mail;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

//@RequestMapping(path = "/api/v1/emails")
public class MailService {

    private Mail mail;
    private UserRepository userRepository;

    public MailService(Mail mail, UserRepository userRepository) {
        this.mail = mail;
        this.userRepository = userRepository;
    }

    public MailService() {
    }

   /* @GetMapping
    public List<Mail> getMails() {
        return userRepository.findAll();
    }

    */

    boolean hasValidFormat(Mail mail) {
        //todo
        return false;
    }
    String hashAlgorithm(Mail mail) {
        //todo
        return "todo";
    }
}
