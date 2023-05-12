package com.example.security;

import com.example.security.security.ForgotPasswordRequestBody;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ForgotPasswordRequestBodyTest {

    @Test
    void testSetEmailAndGetEmail() {
        String email = "test@example.com";

        ForgotPasswordRequestBody requestBody = new ForgotPasswordRequestBody();
        requestBody.setEmail(email);

        Assertions.assertEquals(email, requestBody.getEmail());
    }
}