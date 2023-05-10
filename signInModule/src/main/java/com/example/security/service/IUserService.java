package com.example.security.service;

import com.example.security.model.UserEntity;

public interface IUserService {
    String validatePasswordResetToken(String token);

    UserEntity findUserByPasswordToken(String token);

    void createPasswordResetTokenForUser(UserEntity user, String passwordResetToken);
}
