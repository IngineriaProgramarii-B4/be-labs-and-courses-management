package com.example.security;

import com.example.security.security.JwtAuthEntryPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.Mockito.*;

class JwtAuthEntryPointTest {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private AuthenticationException authException;
    private AuthenticationEntryPoint authEntryPoint;

    @BeforeEach
    void setUp() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        authException = mock(AuthenticationException.class);
        authEntryPoint = new JwtAuthEntryPoint();
    }

    @Test
    void commence_shouldSendUnauthorizedError() throws IOException, ServletException {
        when(authException.getMessage()).thenReturn("Unauthorized");

        authEntryPoint.commence(request, response, authException);

        verify(response).sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }
}
