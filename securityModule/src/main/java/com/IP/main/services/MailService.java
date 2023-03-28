package com.IP.main.services;

import com.IP.main.Mail;
import com.IP.main.UserRepository;

public class MailService extends Mail {

    private Mail mail;
    private UserRepository userRepository;
    public MailService(String mail) {
        super(mail);
    }
    boolean hasValidFormat(Mail mail) {
        //todo
        return false;
    }
    String hashAlgorithm(Mail mail) {
        //todo
        return "todo";
    }
}