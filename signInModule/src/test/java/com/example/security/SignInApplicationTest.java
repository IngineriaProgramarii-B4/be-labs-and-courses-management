package com.example.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.SpringApplication;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SignInApplicationTest {

    @Test
    void main() {
        String[] args = new String[]{"arg1", "arg2"};

        try (MockedStatic<SpringApplication> mockedSpringApplication = Mockito.mockStatic(SpringApplication.class)) {
            SignInApplication.main(args);

            mockedSpringApplication.verify(() -> SpringApplication.run(SignInApplication.class, args));
        }
    }
}