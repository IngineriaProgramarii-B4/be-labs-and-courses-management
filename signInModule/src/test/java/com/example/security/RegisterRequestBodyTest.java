package com.example.security;

import com.example.security.dto.RegisterRequestBody;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RegisterRequestBodyTest {

    @Test
    void testSettersAndGetters() {
        Long userId = 123L;
        String email = "example@example.com";
        String password = "password123";
        String confirmPassword = "password123";

        RegisterRequestBody registerRequestBody = new RegisterRequestBody();
        registerRequestBody.setUserId(userId);
        registerRequestBody.setEmail(email);
        registerRequestBody.setPassword(password);
        registerRequestBody.setConfirmPassword(confirmPassword);

        Assertions.assertEquals(userId, registerRequestBody.getUserId());
        Assertions.assertEquals(email, registerRequestBody.getEmail());
        Assertions.assertEquals(password, registerRequestBody.getPassword());
        Assertions.assertEquals(confirmPassword, registerRequestBody.getConfirmPassword());
    }

    @Test
    void testToString() {
        Long userId = 123L;
        String email = "example@example.com";
        String password = "password123";
        String confirmPassword = "password123";

        RegisterRequestBody registerRequestBody = new RegisterRequestBody();
        registerRequestBody.setUserId(userId);
        registerRequestBody.setEmail(email);
        registerRequestBody.setPassword(password);
        registerRequestBody.setConfirmPassword(confirmPassword);

        String expectedToStringResult = "RegisterRequestBody(userId=" + userId + ", email=" + email +
                ", password=" + password + ", confirmPassword=" + confirmPassword + ")";
        Assertions.assertEquals(expectedToStringResult, registerRequestBody.toString());
    }
}
