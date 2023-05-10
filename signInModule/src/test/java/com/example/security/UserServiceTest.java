package com.example.security;

import com.example.security.exception.StudentNotFoundException;
import com.example.security.model.UserEntity;
import com.example.security.repository.UserRepository;
import com.example.security.service.PasswordResetTokenService;
import com.example.security.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordResetTokenService passwordResetTokenService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, passwordResetTokenService, passwordEncoder);
    }

    @Test
    void testValidatePasswordResetToken() {
        // Mock the behavior of passwordResetTokenService
        String token = "some_token";
        when(passwordResetTokenService.validatePasswordResetToken(token)).thenReturn("Valid");

        String result = userService.validatePasswordResetToken(token);

        assertEquals("Valid", result);
        verify(passwordResetTokenService, times(1)).validatePasswordResetToken(token);
    }

    @Test
    void testFindUserByPasswordToken() {
        // Mock the behavior of passwordResetTokenService
        String token = "some_token";
        UserEntity user = new UserEntity();
        when(passwordResetTokenService.findUserByPasswordToken(token)).thenReturn(Optional.of(user));

        UserEntity result = userService.findUserByPasswordToken(token);

        assertEquals(user, result);
        verify(passwordResetTokenService, times(1)).findUserByPasswordToken(token);
    }

    @Test
    void testFindUserByPasswordToken_ThrowsException() {
        // Mock the behavior of passwordResetTokenService
        String token = "non_existing_token";
        when(passwordResetTokenService.findUserByPasswordToken(token)).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> userService.findUserByPasswordToken(token));
        verify(passwordResetTokenService, times(1)).findUserByPasswordToken(token);
    }

    @Test
    void testCreatePasswordResetTokenForUser() {
        // Mock the behavior of passwordResetTokenService
        UserEntity user = new UserEntity();
        String passwordResetToken = "reset_token";

        userService.createPasswordResetTokenForUser(user, passwordResetToken);

        verify(passwordResetTokenService, times(1)).createPasswordResetTokenForUser(user, passwordResetToken);
    }

    @Test
    void testResetPassword() {
        // Mock the behavior of passwordEncoder and userRepository
        UserEntity user = new UserEntity();
        String newPassword = "new_password";
        String encodedPassword = "encoded_password";
        when(passwordEncoder.encode(newPassword)).thenReturn(encodedPassword);

        userService.resetPassword(user, newPassword);

        assertEquals(encodedPassword, user.getPassword());
        verify(passwordEncoder, times(1)).encode(newPassword);
        verify(userRepository, times(1)).save(user);
    }
}
