package com.example.security;

import com.example.security.model.Role;
import com.example.security.model.UserEntity;
import com.example.security.security.JWTGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class JWTGeneratorTest {

    private JWTGenerator jwtGenerator;
    private UserEntity user;
    private List<Role> roles;
    private Authentication authentication;

    @BeforeEach
    public void setUp() {
        jwtGenerator = new JWTGenerator();

        user = new UserEntity();
        user.setUserId(1L);
        user.setEmail("test@example.com");

        roles = Arrays.asList(new Role());
        authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), "password");
    }

    @Test
    void testGenerateToken() {
        String token = jwtGenerator.generateToken(authentication, roles);

        assertNotNull(token);
        assertEquals(user.getEmail(), jwtGenerator.getEmailFromJWT(token));
    }

    @Test
    void testValidateToken() {
        String token = jwtGenerator.generateToken(authentication, roles);
        assertTrue(jwtGenerator.validateToken(token));
    }

    @Test
    void testGenerateResetToken() {
        String resetToken = jwtGenerator.generateResetToken(user);

        assertNotNull(resetToken);
        assertEquals(user.getEmail(), jwtGenerator.getEmailFromJWT(resetToken));
    }
    @Test
    void testValidateTokenWithInvalidToken() {
        // Generate an invalid token by appending some random characters at the end
        String token = jwtGenerator.generateToken(authentication, roles) + "invalid";

        AuthenticationCredentialsNotFoundException exception = assertThrows(
                AuthenticationCredentialsNotFoundException.class,
                () -> jwtGenerator.validateToken(token)
        );

        assertEquals("JWT was expired or incorrect", exception.getMessage());
    }
}

