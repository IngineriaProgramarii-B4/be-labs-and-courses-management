package com.example.security.service;

import com.example.security.exception.StudentNotFoundException;
import com.example.security.model.UserEntity;
import com.example.security.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final PasswordResetTokenService passwordResetTokenService;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, PasswordResetTokenService passwordResetTokenService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordResetTokenService = passwordResetTokenService;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public String validatePasswordResetToken(String token) {
        return passwordResetTokenService.validatePasswordResetToken(token);
    }
    @Override
    public UserEntity findUserByPasswordToken(String token) {
        Optional<UserEntity> user = passwordResetTokenService.findUserByPasswordToken(token);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new StudentNotFoundException("Student not found " );
             // or throw an exception
        }
    }
    @Override
    public void createPasswordResetTokenForUser(UserEntity user, String passwordResetToken) {
        passwordResetTokenService.createPasswordResetTokenForUser(user, passwordResetToken);
    }
    public void resetPassword(UserEntity theUser, String newPassword) {
        theUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(theUser);
    }
}
