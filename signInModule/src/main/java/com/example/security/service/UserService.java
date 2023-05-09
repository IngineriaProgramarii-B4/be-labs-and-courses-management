package com.example.security.service;

import com.example.security.model.UserEntity;
import com.example.security.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        return passwordResetTokenService.findUserByPasswordToken(token).get();
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
