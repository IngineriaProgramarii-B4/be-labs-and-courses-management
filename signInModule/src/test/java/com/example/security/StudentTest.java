package com.example.security;

import com.example.security.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;

 class StudentTest {

    private Student student;

    @BeforeEach
     void setup() {
        student = new Student(123456L, "John", "Doe", 1, "A1", "john@example.com", "password");
    }

    @Test
     void testHashPassword() {
        student.hashPassword();

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // check that the password has been hashed
        assertTrue(passwordEncoder.matches("password", student.getPassword()));
    }
}