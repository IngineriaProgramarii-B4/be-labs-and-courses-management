package com.example.demo.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) { this.userRepository = userRepository;}

    public UserRepository getUserRepository() {
        return userRepository;
    }

    @Transactional
    public void addUser(User user) {
        user.hashPassword();
        userRepository.save(user);
    }

}