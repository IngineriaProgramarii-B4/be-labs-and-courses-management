package com.example.security;

import com.example.security.security.CustomUserDetailsService;
import com.example.security.security.JWTGenerator;
import com.example.security.security.JwtAuthenticationFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    private JWTGenerator tokenGenerator;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    private String token;

    @BeforeEach
    public void setUp() {
        token = "sampleToken";
    }

    @Test
    void testDoFilterInternal() throws ServletException, IOException {
        String email = "test@example.com";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(tokenGenerator.validateToken(token)).thenReturn(true);
        when(tokenGenerator.getEmailFromJWT(token)).thenReturn(email);

        UserDetails userDetails = mock(UserDetails.class);
        when(customUserDetailsService.loadUserByUsername(email)).thenReturn(userDetails);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(tokenGenerator).validateToken(token);
        verify(tokenGenerator).getEmailFromJWT(token);
        verify(customUserDetailsService).loadUserByUsername(email);
        verify(filterChain).doFilter(request, response);
    }
    @Test
    void testDoFilterInternal_invalidToken() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(tokenGenerator.validateToken(token)).thenReturn(false);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(tokenGenerator).validateToken(token);
        verify(tokenGenerator, never()).getEmailFromJWT(token);
        verify(customUserDetailsService, never()).loadUserByUsername(any());
        verify(filterChain).doFilter(request, response);
    }
    @Test
    void testDoFilterInternal_noBearerToken() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(token);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(tokenGenerator, never()).validateToken(token);
        verify(tokenGenerator, never()).getEmailFromJWT(token);
        verify(customUserDetailsService, never()).loadUserByUsername(any());
        verify(filterChain).doFilter(request, response);
    }
    @Test
    void testDoFilterInternal_nullToken() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(tokenGenerator, never()).validateToken(any());
        verify(tokenGenerator, never()).getEmailFromJWT(any());
        verify(customUserDetailsService, never()).loadUserByUsername(any());
        verify(filterChain).doFilter(request, response);
    }
}
