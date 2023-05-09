package com.example.demo.service;

import com.example.models.Teacher;
import com.example.repository.TeachersRepository;
import com.example.services.TeachersService;
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
class TeachersServiceTest {

    @InjectMocks
    TeachersService teachersService;

    @Mock
    TeachersRepository teachersRepository;

    private Teacher teacher;

    @BeforeEach
    public void setup() {
        teacher = new Teacher(
                "FirstnameTest",
                "LastnameTest",
                "EmailTest@gmail.com",
                "UsernameTest",
                "OfficeTest",
                null,
                "TitleTest"
        );
    }

    @Test
    void getTeachersByParamsOfficeTest() {

        List<Teacher> expected = List.of(teacher);

        Map<String, Object> args = new HashMap<>();

        args.put("office", "OfficeTest");

        args.put("id", "");

        given(teachersRepository.findTeachersByParams(
                nullable(UUID.class),
                nullable(String.class),
                nullable(String.class),
                nullable(String.class),
                nullable(String.class),
                eq("OfficeTest"),
                nullable(String.class)))
                .willReturn(expected);

        List<Teacher> result = teachersService.getTeachersByParams(args);

        assertTrue(result.containsAll(expected));
    }

    @Test
    void getTeachersByParamsTitleTest() {

        List<Teacher> expected = List.of(teacher);

        Map<String, Object> args = new HashMap<>();

        args.put("title", "TitleTest");

        given(teachersRepository.findTeachersByParams(
                nullable(UUID.class),
                nullable(String.class),
                nullable(String.class),
                nullable(String.class),
                nullable(String.class),
                nullable(String.class),
                eq("TitleTest")))
                .willReturn(expected);

        List<Teacher> result = teachersService.getTeachersByParams(args);

        assertTrue(result.containsAll(expected));
    }

    @Test
    void getTeachersByParamsIdTest() {

        List<Teacher> expected = List.of(teacher);

        Map<String, Object> args = new HashMap<>();

        UUID idTest = UUID.randomUUID();

        args.put("id", idTest);

        given(teachersRepository.findTeachersByParams(
                eq(idTest),
                nullable(String.class),
                nullable(String.class),
                nullable(String.class),
                nullable(String.class),
                nullable(String.class),
                nullable(String.class)))
                .willReturn(expected);

        List<Teacher> result = teachersService.getTeachersByParams(args);

        assertTrue(result.containsAll(expected));
    }

    @Test
    void saveTeacherTest() {
        when(teachersRepository.save(teacher)).thenReturn(teacher);

        teachersService.saveTeacher(teacher);

        verify(teachersRepository, times(1)).save(teacher);
    }

    @Test
    void updateTeacherTest() {
        doNothing().when(teachersRepository).updateTeacher(teacher.getId(), teacher.getFirstname(), teacher.getLastname(), teacher.getEmail(), teacher.getUsername(), teacher.getOffice(), teacher.getTitle());

        teachersService.updateTeacher(teacher.getId(), teacher);

        verify(teachersRepository, times(1)).updateTeacher(teacher.getId(), teacher.getFirstname(), teacher.getLastname(), teacher.getEmail(), teacher.getUsername(), teacher.getOffice(), teacher.getTitle());
    }
}