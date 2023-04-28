package com.example.security;

import com.example.security.controllers.AuthController;
import com.example.security.dto.AuthResponseDto;
import com.example.security.dto.LoginRequestBody;
import com.example.security.dto.RegisterRequestBody;
import com.example.security.model.Role;
import com.example.security.model.UserEntity;
import com.example.security.repository.RoleRepository;
import com.example.security.repository.UserRepository;
import com.example.security.security.JWTGenerator;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
 class AuthControllerTest {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JWTGenerator jwtGenerator;
    private AuthController authController;

    @BeforeEach
    public void setUp() {
        authenticationManager = Mockito.mock(AuthenticationManager.class);
        userRepository = Mockito.mock(UserRepository.class);
        roleRepository = Mockito.mock(RoleRepository.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        jwtGenerator = Mockito.mock(JWTGenerator.class);

        authController = new AuthController(authenticationManager, userRepository, roleRepository, passwordEncoder, jwtGenerator);
    }

    @Test
     void testLogin() {
        LoginRequestBody loginRequestBody = new LoginRequestBody();
        loginRequestBody.setEmail("test@example.com");
        loginRequestBody.setPassword("password");

        UserEntity user = new UserEntity();
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setRoles(Collections.singletonList(new Role()));

        Authentication authentication = Mockito.mock(Authentication.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        when(authenticationManager.authenticate(Mockito.any())).thenReturn(authentication);
        when(userRepository.findByEmail("test@example.com")).thenReturn(user);
        when(jwtGenerator.generateToken(authentication, user.getRoles())).thenReturn("test-token");
        when(request.getCookies()).thenReturn(null);

        ResponseEntity<AuthResponseDto> response = authController.login(loginRequestBody, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("test-token", response.getBody().getAccessToken());
    }
     @Test
     void testRegister_emailAlreadyInUse() {
         // Arrange
         RegisterRequestBody registerRequestBody = new RegisterRequestBody();
         registerRequestBody.setUserId(1L);
         registerRequestBody.setEmail("test@example.com");
         registerRequestBody.setPassword("password");

         when(userRepository.existsByEmail("test@example.com")).thenReturn(true);

         // Act
         ResponseEntity<String> response = authController.register(registerRequestBody);

         // Assert
         assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
         assertEquals("Email is already in use!", response.getBody());
         verify(userRepository).existsByEmail("test@example.com");
     }


     @Test
     void testRegister() {
        RegisterRequestBody registerRequestBody = new RegisterRequestBody();
        registerRequestBody.setUserId(1L);
        registerRequestBody.setEmail("test@example.com");
        registerRequestBody.setPassword("password");

        UserEntity user = new UserEntity();
        user.setUserId(1L);


       when(userRepository.existsById(1L)).thenReturn(true);
        when(userRepository.findByUserId(1L)).thenReturn(user);
        when(passwordEncoder.encode("password")).thenReturn("encoded-password");
         when(userRepository.existsByEmail(registerRequestBody.getEmail())).thenReturn(false);

         ResponseEntity<String> response = authController.register(registerRequestBody);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User registered success!", response.getBody());
    }
}
