package com.example.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SignInApplicationTests {

    @InjectMocks
    private SignInApplication signInApplication;

    @Test
    void corsConfigurer_addCorsMappings() {
        // Given
        CorsRegistry corsRegistry = mock(CorsRegistry.class);
        CorsRegistration corsRegistration = mock(CorsRegistration.class);
        when(corsRegistry.addMapping(anyString())).thenReturn(corsRegistration);
        when(corsRegistration.allowedOrigins(anyString())).thenReturn(corsRegistration);
        when(corsRegistration.allowedMethods(any())).thenReturn(corsRegistration);
        when(corsRegistration.allowedHeaders(anyString())).thenReturn(corsRegistration);
        when(corsRegistration.allowCredentials(anyBoolean())).thenReturn(corsRegistration);

        // When
        signInApplication.corsConfigurer().addCorsMappings(corsRegistry);

        // Then
        verify(corsRegistry).addMapping("/**");
        verify(corsRegistration).allowedOrigins("http://localhost:3000");
        verify(corsRegistration).allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS");
        verify(corsRegistration).allowedHeaders("*");
        verify(corsRegistration).allowCredentials(true);
    }
}

