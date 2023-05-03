package com.example.demo.service;

import com.example.models.Student;
import com.example.repository.StudentsRepository;
import com.example.services.StudentsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudentsServiceTest {

    @InjectMocks
    StudentsService studentsService;

    @Mock
    StudentsRepository studentsRepository;

    private Student student;

    @BeforeEach
    public void setup() {
        student = new Student(
                "FirstnameTest",
                "LastnameTest",
                "EmailTest@gmail.com",
                "UsernameTest",
                1,
                2,
                "RegNumberTest",
                null
        );
    }

    @Test
    void getStudentsByParamsYearTest() {

        List<Student> expected = List.of(student);

        Map<String, Object> args = new HashMap<>();

        args.put("year", "1");

        given(studentsRepository.findStudentsByParams(
                nullable(UUID.class),
                nullable(String.class),
                nullable(String.class),
                nullable(String.class),
                nullable(String.class),
                eq(1),
                anyInt(),
                nullable(String.class)))
                .willReturn(expected);

        List<Student> result = studentsService.getStudentsByParams(args);

        assertTrue(result.containsAll(expected));
    }

    @Test
    void getStudentsByParamsSemesterTest() {

        List<Student> expected = List.of(student);

        Map<String, Object> args = new HashMap<>();

        args.put("semester", "2");

        given(studentsRepository.findStudentsByParams(
                nullable(UUID.class),
                nullable(String.class),
                nullable(String.class),
                nullable(String.class),
                nullable(String.class),
                anyInt(),
                eq(2),
                nullable(String.class)))
                .willReturn(expected);

        List<Student> result = studentsService.getStudentsByParams(args);

        assertTrue(result.containsAll(expected));
    }

    @Test
    void getStudentsByParamsRegistrationNumberTest() {

        List<Student> expected = List.of(student);

        Map<String, Object> args = new HashMap<>();

        args.put("registrationNumber", "RegNumberTest");

        given(studentsRepository.findStudentsByParams(
                nullable(UUID.class),
                nullable(String.class),
                nullable(String.class),
                nullable(String.class),
                nullable(String.class),
                anyInt(),
                anyInt(),
                eq("RegNumberTest")))
                .willReturn(expected);

        List<Student> result = studentsService.getStudentsByParams(args);

        assertTrue(result.containsAll(expected));
    }

    @Test
    void saveStudentTest() {

        StudentsService studentsServiceMock = mock(StudentsService.class);

        ArgumentCaptor<Student> studentToCapture = ArgumentCaptor.forClass(Student.class);

        doNothing().when(studentsServiceMock).saveStudent(studentToCapture.capture());

        studentsServiceMock.saveStudent(student);

        assertEquals(student, studentToCapture.getValue());
    }
}