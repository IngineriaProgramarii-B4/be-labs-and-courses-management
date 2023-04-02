package com.example.ProiectIP.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Password {
    private String password;
    public Password(String password) {
        this.password = password;
    }
    public String hashAlgorithm() {
        //todo
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
    /*
    boolean hasValidFormat(Password password) {
        //todo
        return true;
    }

     */
}
