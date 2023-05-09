package com.example.security.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PasswordResetRequest {
    private String newPassword;
}
