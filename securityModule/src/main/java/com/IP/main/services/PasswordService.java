package com.IP.main.services;

import com.IP.main.Password;
import com.IP.main.UserRepository;

public class PasswordService extends Password {
    private Password password;
    private UserRepository userRepository;
    public PasswordService(String password, Password password1) {
        super(password);
    }
    boolean hasValidFormat(Password password) {
        //todo
        return false;
    }
    String hashAlgorithm(Password password) {
        //todo
        return "todo";
    }
}