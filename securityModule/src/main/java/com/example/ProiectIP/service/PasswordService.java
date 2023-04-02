package com.example.ProiectIP.service;
import com.example.ProiectIP.dao.UserRepository;
import com.example.ProiectIP.model.Password;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class PasswordService{
    private Password password;
    private UserRepository userRepository;
    public PasswordService(Password password, UserRepository userRepository) {
        this.password = password;
        this.userRepository = userRepository;
    }


}
