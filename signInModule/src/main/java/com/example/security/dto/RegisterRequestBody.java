package com.example.security.dto;

import lombok.Data;

@Data
public class RegisterRequestBody {
    private Long userId;
    private String email;
    private String password;
    private String confirmPassword;

}
