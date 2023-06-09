package com.example.demo.service;

import com.example.models.Student;
import com.example.repository.StudentsRepository;
import com.example.services.StudentsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.*;

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
        //Given
        List<Student> expected = List.of(student);

        Map<String, Object> args = new HashMap<>();

        args.put("year", "1");

        args.put("semester", "");

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

        //When
        List<Student> result = studentsService.getStudentsByParams(args);

        //Then
        assertTrue(result.containsAll(expected));
    }

    @Test
    void getStudentsByParamsSemesterTest() {
        //Given
        List<Student> expected = List.of(student);

        Map<String, Object> args = new HashMap<>();

        args.put("semester", "2");

        args.put("id", "");
        args.put("year", "");

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

        //When
        List<Student> result = studentsService.getStudentsByParams(args);

        //Then
        assertTrue(result.containsAll(expected));
    }

    @Test
    void getStudentsByParamsRegistrationNumberTest() {
        //Given
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

        //When
        List<Student> result = studentsService.getStudentsByParams(args);

        //Then
        assertTrue(result.containsAll(expected));
    }

    @Test
    void getStudentsByParamsIdTest() {
        //Given
        List<Student> expected = List.of(student);

        Map<String, Object> args = new HashMap<>();

        UUID idTest = UUID.randomUUID();

        args.put("id", idTest);

        given(studentsRepository.findStudentsByParams(
                eq(idTest),
                nullable(String.class),
                nullable(String.class),
                nullable(String.class),
                nullable(String.class),
                anyInt(),
                anyInt(),
                nullable(String.class)))
                .willReturn(expected);

        //When
        List<Student> result = studentsService.getStudentsByParams(args);

        //Then
        assertTrue(result.containsAll(expected));
    }

    @Test
    void saveStudentTest() {
        //When
        when(studentsRepository.save(student)).thenReturn(student);

        studentsService.saveStudent(student);

        //Then
        verify(studentsRepository, times(1)).save(student);
    }

    @Test
    void updateStudentTest() {
        //When
        doNothing().when(studentsRepository).updateStudent(student.getId(), student.getFirstname(), student.getLastname(), student.getEmail(), student.getUsername(), student.getYear(), student.getSemester(), student.getRegistrationNumber());

        studentsService.updateStudent(student.getId(), student);

        //Then
        verify(studentsRepository, times(1)).updateStudent(student.getId(), student.getFirstname(), student.getLastname(), student.getEmail(), student.getUsername(), student.getYear(), student.getSemester(), student.getRegistrationNumber());
    }
}