package com.example.security.controllers;

import com.example.security.dto.AuthResponseDto;
import com.example.security.dto.LoginRequestBody;
import com.example.security.dto.RegisterRequestBody;
import com.example.security.model.Role;
import com.example.security.model.UserEntity;
import com.example.security.repository.RoleRepository;
import com.example.security.repository.UserRepository;
import com.example.security.security.JWTGenerator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.example.security.security.ForgotPasswordRequestBody;
import com.example.security.security.EmailService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final JWTGenerator jwtGenerator;

    private final EmailService emailService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator, EmailService emailService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
        this.emailService = emailService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginRequestBody loginRequestBody,HttpServletRequest request ) {
        List<Role> roles = new ArrayList<>();
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestBody.getEmail(),
                loginRequestBody.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserEntity user = userRepository.findByEmail(loginRequestBody.getEmail());
        if(user !=null && !user.getRoles().isEmpty()){
            roles = user.getRoles();
        }
        String token = jwtGenerator.generateToken(authentication,roles);
        return new ResponseEntity<>(new AuthResponseDto(token), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestBody registerRequestBody) {
        UserEntity user;
        try {
            if (userRepository.existsByEmail(registerRequestBody.getEmail())) {
                return new ResponseEntity<>("Email is already in use!", HttpStatus.BAD_REQUEST);
            }

            if (userRepository.existsById(registerRequestBody.getUserId())) {
                user = userRepository.findByUserId(registerRequestBody.getUserId());
                if (StringUtils.hasText(user.getEmail()) && StringUtils.hasText(user.getPassword())) {
                    return new ResponseEntity<>("User is already registered!", HttpStatus.BAD_REQUEST);
                } else {
                    user.setEmail(registerRequestBody.getEmail());
                    user.setPassword(passwordEncoder.encode(registerRequestBody.getPassword()));
                    userRepository.save(user);
                }
            } else {
                return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error when saving user!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("User registered success!", HttpStatus.OK);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequestBody forgotPasswordRequestBody) {
        String email = forgotPasswordRequestBody.getEmail();
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
        }

        try {
            String resetToken = jwtGenerator.generateResetToken(user);
            emailService.sendPasswordResetEmail(user, resetToken);
            return new ResponseEntity<>("Password reset email sent!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error when sending password reset email!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
