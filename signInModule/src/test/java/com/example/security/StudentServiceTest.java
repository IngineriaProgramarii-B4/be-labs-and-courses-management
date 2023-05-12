package com.example.security;

import com.example.security.exception.StudentNotFoundException;
import com.example.security.model.Student;
import com.example.security.repository.StudentRepository;
import com.example.security.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    private StudentRepository studentRepository;
    private PasswordEncoder passwordEncoder;
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        studentRepository = mock(StudentRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        studentService = new StudentService(studentRepository, passwordEncoder);
    }

    @Test
    void testAddStudent() {
        Student student = new Student();
        student.setRegistrationNumber(12345L);

        when(studentRepository.save(student)).thenReturn(student);

        studentService.addStudent(student);

        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void testUpdateStudent() {
        Long registrationNumber = 12345L;
        String newPassword = "newPassword";
        Student student = new Student();
        student.setRegistrationNumber(registrationNumber);
        student.setPassword("oldPassword");

        when(studentRepository.findByRegistrationNumber(registrationNumber)).thenReturn(student);
        when(passwordEncoder.encode(newPassword)).thenReturn("encodedNewPassword");

        assertThrows(StudentNotFoundException.class, () -> studentService.updateStudent(registrationNumber, newPassword));

        verify(studentRepository, times(1)).findByRegistrationNumber(registrationNumber);
        verify(passwordEncoder, times(1)).encode(newPassword);
    }

    @Test
    void testGetStudentByRegistrationNumber() {
        Long registrationNumber = 12345L;
        Student student = new Student();
        student.setRegistrationNumber(registrationNumber);

        when(studentRepository.findByRegistrationNumber(registrationNumber)).thenReturn(student);

        Student found = studentService.getStudentByRegistrationNumber(registrationNumber);

        assertEquals(found, student);
        verify(studentRepository, times(1)).findByRegistrationNumber(registrationNumber);
    }
    @Test
    void testUpdateStudent_WhenStudentNotFound() {
        Long registrationNumber = 12345L;
        String newPassword = "newPassword";

        when(studentRepository.findByRegistrationNumber(registrationNumber)).thenReturn(null);

        assertThrows(StudentNotFoundException.class, () -> studentService.updateStudent(registrationNumber, newPassword));

        verify(studentRepository, times(1)).findByRegistrationNumber(registrationNumber);
        verifyNoMoreInteractions(passwordEncoder);
        verifyNoMoreInteractions(studentRepository);
    }
}
