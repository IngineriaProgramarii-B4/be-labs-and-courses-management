package com.example.demo.service;

import com.example.models.Student;
import com.example.models.User;
import com.example.repository.UsersRepository;
import com.example.services.UsersService;
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


@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class UsersServiceTest {

    @InjectMocks
    UsersService usersService;

    @Mock
    UsersRepository usersRepository;

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
    void findingUsersByIdTest() {

        List<User> expected = List.of(student);

        Map<String, Object> args = new HashMap<>();

        args.put("id", "0f14d0ab-9605-4a62-a9e4-5ed26688389b");

        given(usersRepository.findUserByParams(
                eq(UUID.fromString("0f14d0ab-9605-4a62-a9e4-5ed26688389b")),
                nullable(String.class),
                nullable(String.class),
                nullable(String.class),
                nullable(String.class)))
                .willReturn(expected);

        List<User> result = usersService.getUsersByParams(args);

        assertTrue(result.containsAll(expected));
    }

    @Test
    void findingUsersByFirstnameTest() {

        List<User> expected = List.of(student);

        Map<String, Object> args = new HashMap<>();

        args.put("firstname", "FirstnameTest");

        given(usersRepository.findUserByParams(
                nullable(UUID.class),
                eq("FirstnameTest"),
                nullable(String.class),
                nullable(String.class),
                nullable(String.class)))
                .willReturn(expected);

        List<User> result = usersService.getUsersByParams(args);

        assertTrue(result.containsAll(expected));
    }

    @Test
    void findingUsersByLastnameTest() {

        List<User> expected = List.of(student);

        Map<String, Object> args = new HashMap<>();

        args.put("lastname", "LastnameTest");

        given(usersRepository.findUserByParams(
                nullable(UUID.class),
                nullable(String.class),
                eq("LastnameTest"),
                nullable(String.class),
                nullable(String.class)))
                .willReturn(expected);

        List<User> result = usersService.getUsersByParams(args);

        assertTrue(result.containsAll(expected));
    }

    @Test
    void findingUsersByEmailTest() {

        List<User> expected = List.of(student);

        Map<String, Object> args = new HashMap<>();

        args.put("email", "test@gmail.com");

        given(usersRepository.findUserByParams(
                nullable(UUID.class),
                nullable(String.class),
                nullable(String.class),
                eq("test@gmail.com"),
                nullable(String.class)))
                .willReturn(expected);

        List<User> result = usersService.getUsersByParams(args);

        assertTrue(result.containsAll(expected));
    }

    @Test
    void findingUsersByUsernameTest() {

        List<User> expected = List.of(student);

        Map<String, Object> args = new HashMap<>();

        args.put("username", "UsernameTest");

        given(usersRepository.findUserByParams(
                nullable(UUID.class),
                nullable(String.class),
                nullable(String.class),
                nullable(String.class),
                eq("UsernameTest")))
                .willReturn(expected);

        List<User> result = usersService.getUsersByParams(args);

        assertTrue(result.containsAll(expected));
    }
}