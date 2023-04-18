package com.example.demo.service;

import com.example.models.Admin;
import com.example.models.Teacher;
import com.example.repository.TeachersRepository;
import com.example.services.UsersService;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;


@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class TeachersServiceTest {

    @InjectMocks
    UsersService usersService;

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
    void findingTeachersByOfficeTest() {

        List<Teacher> expected = List.of(teacher);

        Map<String, Object> args = new HashMap<>();

        args.put("office", "OfficeTest");

        given(teachersRepository.findTeachersByParams(
                nullable(UUID.class),
                nullable(String.class),
                nullable(String.class),
                nullable(String.class),
                nullable(String.class),
                eq("OfficeTest"),
                nullable(String.class)))
                .willReturn(expected);

        List<Teacher> result = usersService.getTeachersByParams(args);

        assertTrue(result.containsAll(expected));
    }

    @Test
    void findingTeachersByTitleTest() {

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

        List<Teacher> result = usersService.getTeachersByParams(args);

        assertTrue(result.containsAll(expected));
    }

    @Test
    void saveAdminTest() {

        UsersService usersServiceMock = mock(UsersService.class);

        ArgumentCaptor<Teacher> adminToCapture = ArgumentCaptor.forClass(Teacher.class);

        doNothing().when(usersServiceMock).saveTeacher(adminToCapture.capture());

        usersServiceMock.saveTeacher(teacher);

        assertEquals(teacher, adminToCapture.getValue());
    }
}