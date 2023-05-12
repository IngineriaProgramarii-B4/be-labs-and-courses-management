package com.example.security;

import com.example.security.model.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TeacherTest {

    private Teacher teacher;

    @BeforeEach
    void setup() {
        teacher = new Teacher("John", "Doe", "Professor", "john@example.com", "password", 123456L);
    }

    @Test
    void testHashPassword() {
        teacher.hashPassword();

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // check that the password has been hashed
        assertTrue(passwordEncoder.matches("password", teacher.getPassword()));
    }
    @Test
    void testSetId() {
        UUID id = UUID.randomUUID();
        teacher.setId(id);

        assertEquals(id, teacher.getId());
    }

    @Test
    void testSetMail() {
        String mail = "newmail@example.com";
        teacher.setMail(mail);

        assertEquals(mail, teacher.getMail());
    }

    @Test
    void testSetCreatedAt() {
        Date createdAt = new Date();
        teacher.setCreatedAt(createdAt);

        assertEquals(createdAt, teacher.getCreatedAt());
    }

    @Test
    void testSetUpdatedAt() {
        Date updatedAt = new Date();
        teacher.setUpdatedAt(updatedAt);

        assertEquals(updatedAt, teacher.getUpdatedAt());
    }

    @Test
    void testSetFirstName() {
        String firstName = "Jane";
        teacher.setFirstName(firstName);

        assertEquals(firstName, teacher.getFirstName());
    }

    @Test
    void testSetLastName() {
        String lastName = "Smith";
        teacher.setLastName(lastName);

        assertEquals(lastName, teacher.getLastName());
    }

    @Test
    void testSetDegree() {
        String degree = "Associate Professor";
        teacher.setDegree(degree);

        assertEquals(degree, teacher.getDegree());
    }

    @Test
    void testGetCreatedAt() {
        assertNull(teacher.getCreatedAt());
    }
}