package com.example.demo.service;

import com.example.models.Admin;
import com.example.repository.AdminsRepository;
import com.example.services.AdminsService;
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
class AdminsServiceTest {

    @InjectMocks
    AdminsService adminsService;

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
    void getAdminsByParamsOfficeTest() {

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

        List<Admin> result = adminsService.getAdminsByParams(args);

        assertTrue(result.containsAll(expected));
    }

    @Test
    void getAdminsByParamsDepartmentTest() {

        List<Admin> expected = List.of(admin);

        Map<String, Object> args = new HashMap<>();

        args.put("department", "DepartmentTest");

        args.put("id", "");

        given(adminsRepository.findAdminsByParams(
                nullable(UUID.class),
                nullable(String.class),
                nullable(String.class),
                nullable(String.class),
                nullable(String.class),
                nullable(String.class),
                eq("DepartmentTest")))
                .willReturn(expected);

        List<Admin> result = adminsService.getAdminsByParams(args);

        assertTrue(result.containsAll(expected));
    }

    @Test
    void getAdminsByParamsIdTest() {

        List<Admin> expected = List.of(admin);

        Map<String, Object> args = new HashMap<>();

        UUID idTest = UUID.randomUUID();

        args.put("id", idTest);

        given(adminsRepository.findAdminsByParams(
                eq(idTest),
                nullable(String.class),
                nullable(String.class),
                nullable(String.class),
                nullable(String.class),
                nullable(String.class),
                nullable(String.class)))
                .willReturn(expected);

        List<Admin> result = adminsService.getAdminsByParams(args);

        assertTrue(result.containsAll(expected));
    }

    @Test
    void saveAdminTest() {
        when(adminsRepository.save(admin)).thenReturn(admin);
        adminsService.saveAdmin(admin);

        verify(adminsRepository, times(1)).save(admin);
    }

    @Test
    void updateAdminTest() {
        doNothing().when(adminsRepository).updateAdmin(admin.getId(), admin.getFirstname(), admin.getLastname(), admin.getEmail(), admin.getUsername(), admin.getOffice(), admin.getDepartment());

        adminsService.updateAdmin(admin.getId(), admin);

        verify(adminsRepository, times(1)).updateAdmin(admin.getId(), admin.getFirstname(), admin.getLastname(), admin.getEmail(), admin.getUsername(), admin.getOffice(), admin.getDepartment());
    }
}