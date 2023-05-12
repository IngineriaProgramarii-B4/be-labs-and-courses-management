package com.example.security.service;

import com.example.security.model.PasswordResetToken;
import com.example.security.model.UserEntity;
import com.example.security.repository.PasswordResetTokenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

import java.util.Calendar;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PasswordResetTokenServiceTest {

    @InjectMocks
    private PasswordResetTokenService passwordResetTokenService;

    @Mock
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Test
    void testCreatePasswordResetTokenForUser() {
        UserEntity user = new UserEntity();
        String token = "testToken";

        passwordResetTokenService.createPasswordResetTokenForUser(user, token);

        verify(passwordResetTokenRepository, times(1)).save(any(PasswordResetToken.class));
    }

    @Test
    void testValidatePasswordResetTokenValid() {
        String token = "testToken";

        PasswordResetToken passwordResetToken = new PasswordResetToken(token);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 1); // set token expiration to 1 hour in the future
        passwordResetToken.setExpirationTime(calendar.getTime());

        when(passwordResetTokenRepository.findByToken(token)).thenReturn(passwordResetToken);

        String result = passwordResetTokenService.validatePasswordResetToken(token);

        assertEquals("valid", result);
    }

    @Test
    void testValidatePasswordResetTokenExpired() {
        String token = "testToken";

        PasswordResetToken passwordResetToken = new PasswordResetToken(token);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -1); // set token expiration to 1 hour in the past
        passwordResetToken.setExpirationTime(calendar.getTime());

        when(passwordResetTokenRepository.findByToken(token)).thenReturn(passwordResetToken);

        String result = passwordResetTokenService.validatePasswordResetToken(token);

        assertEquals("Link already expired, resend link", result);
    }

    @Test
    void testValidatePasswordResetTokenInvalid() {
        String token = "testToken";

        when(passwordResetTokenRepository.findByToken(token)).thenReturn(null);

        String result = passwordResetTokenService.validatePasswordResetToken(token);

        assertEquals("Invalid verification token", result);
    }

    @Test
    void testFindUserByPasswordToken() {
        String token = "testToken";
        UserEntity user = new UserEntity();

        PasswordResetToken passwordResetToken = new PasswordResetToken(token);
        passwordResetToken.setUser(user);

        when(passwordResetTokenRepository.findByToken(token)).thenReturn(passwordResetToken);

        Optional<UserEntity> result = passwordResetTokenService.findUserByPasswordToken(token);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void testFindPasswordResetToken() {
        String token = "testToken";
        PasswordResetToken passwordResetToken = new PasswordResetToken(token);

        when(passwordResetTokenRepository.findByToken(token)).thenReturn(passwordResetToken);

        PasswordResetToken result = passwordResetTokenService.findPasswordResetToken(token);

        assertEquals(passwordResetToken, result);
    }
}