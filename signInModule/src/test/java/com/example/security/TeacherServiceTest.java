package com.example.security;

import com.example.security.exception.StudentNotFoundException;
import com.example.security.model.Teacher;
import com.example.security.repository.TeacherRepository;
import com.example.security.service.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TeacherServiceTest {

    private TeacherService teacherService;

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        teacherService = new TeacherService(teacherRepository, passwordEncoder);
    }

    @Test
    void testAddTeacher() {
        Teacher teacher = new Teacher();
        teacher.setRegistrationNumber(12345L);

        when(teacherRepository.save(teacher)).thenReturn(teacher);

        teacherService.addTeacher(teacher);

        verify(teacherRepository, times(1)).save(teacher);
    }

    @Test
    void testUpdateTeacher() {
        Long registrationNumber = 12345L;
        String newPassword = "newPassword";
        Teacher teacher = new Teacher();
        teacher.setRegistrationNumber(registrationNumber);
        teacher.setPassword("oldPassword");

        when(teacherRepository.findByRegistrationNumber(registrationNumber)).thenReturn(teacher);
        when(passwordEncoder.encode(newPassword)).thenReturn("encodedNewPassword");

        assertThrows(StudentNotFoundException.class, () -> teacherService.updateStudent(registrationNumber, newPassword));

        verify(teacherRepository, times(1)).findByRegistrationNumber(registrationNumber);
        verify(passwordEncoder, times(1)).encode(newPassword);
    }

    @Test
    void testGetTeacherByRegistrationNumber() {
        Long registrationNumber = 12345L;
        Teacher teacher = new Teacher();
        teacher.setRegistrationNumber(registrationNumber);

        when(teacherRepository.findByRegistrationNumber(registrationNumber)).thenReturn(teacher);

        Teacher found = teacherService.getTeacherByRegistrationNumber(registrationNumber);

        assertEquals(found, teacher);
        verify(teacherRepository, times(1)).findByRegistrationNumber(registrationNumber);
    }
    @Test
    void testUpdateTeacher_WhenTeacherNotFound() {
        Long registrationNumber = 12345L;
        String newPassword = "newPassword";

        when(teacherRepository.findByRegistrationNumber(registrationNumber)).thenReturn(null);

        assertThrows(StudentNotFoundException.class, () -> teacherService.updateStudent(registrationNumber, newPassword));

        verify(teacherRepository, times(1)).findByRegistrationNumber(registrationNumber);
        verifyNoMoreInteractions(passwordEncoder);
        verifyNoMoreInteractions(teacherRepository);
    }

}