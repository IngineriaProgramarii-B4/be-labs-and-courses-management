package com.example.demo.service;

import com.example.models.Admin;
import com.example.models.Student;
import com.example.models.User;
import com.example.repository.AdminsRepository;
import com.example.repository.UsersRepository;
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
public class AdminsServiceTest {

    @InjectMocks
    UsersService usersService;

    @Mock
    AdminsRepository adminsRepository;

    private Admin admin;

    @BeforeEach
    public void setup() {
        admin = new Admin(
                "FirstnameTest",
                "LastnameTest",
                "EmailTest@gmail.com",
                "UsernameTest",
                "OfficeTest",
                "DepartmentTest"
        );
    }

    @Test
    void findingAdminsByOfficeTest() {

        List<Admin> expected = List.of(admin);

        Map<String, Object> args = new HashMap<>();

        args.put("office", "OfficeTest");

        given(adminsRepository.findAdminsByParams(
                nullable(UUID.class),
                nullable(String.class),
                nullable(String.class),
                nullable(String.class),
                nullable(String.class),
                eq("OfficeTest"),
                nullable(String.class)))
                .willReturn(expected);

        List<Admin> result = usersService.getAdminsByParams(args);

        assertTrue(result.containsAll(expected));
    }

    @Test
    void findingAdminsByDepartmentTest() {

        List<Admin> expected = List.of(admin);

        Map<String, Object> args = new HashMap<>();

        args.put("department", "DepartmentTest");

        given(adminsRepository.findAdminsByParams(
                nullable(UUID.class),
                nullable(String.class),
                nullable(String.class),
                nullable(String.class),
                nullable(String.class),
                nullable(String.class),
                eq("DepartmentTest")))
                .willReturn(expected);

        List<Admin> result = usersService.getAdminsByParams(args);

        assertTrue(result.containsAll(expected));
    }

    @Test
    void saveAdminTest() {

        UsersService usersServiceMock = mock(UsersService.class);

        ArgumentCaptor<Admin> adminToCapture = ArgumentCaptor.forClass(Admin.class);

        doNothing().when(usersServiceMock).saveAdmin(adminToCapture.capture());

        usersServiceMock.saveAdmin(admin);

        assertEquals(admin, adminToCapture.getValue());
    }
}