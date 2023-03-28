package com.example.services;

import com.example.models.Student;
import com.example.models.AppUser;
import com.example.repository.UserRepository;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public AppUser getUser(String username) {
        AppUser user = userRepository.findUserByUsername(username).orElse(null);
        return user;
    }
}
