package com.example.demo.objects;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Password {
    private String password;
    public Password(String password) {
        this.password = password;
    }
    public String hash() {
        //todo
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    public String getPassword() {
        return password;
    }
}
