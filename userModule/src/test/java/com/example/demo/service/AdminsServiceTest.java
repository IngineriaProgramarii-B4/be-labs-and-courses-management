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
        //Given
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

        //When
        List<Admin> result = adminsService.getAdminsByParams(args);

        //Then
        assertTrue(result.containsAll(expected));
    }

    @Test
    void getAdminsByParamsDepartmentTest() {
        //Given
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

        //When
        List<Admin> result = adminsService.getAdminsByParams(args);

        //Then
        assertTrue(result.containsAll(expected));
    }

    @Test
    void getAdminsByParamsIdTest() {
        //Given
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

        //When
        List<Admin> result = adminsService.getAdminsByParams(args);

        //Then
        assertTrue(result.containsAll(expected));
    }

    @Test
    void saveAdminTest() {
        //When
        when(adminsRepository.save(admin)).thenReturn(admin);
        adminsService.saveAdmin(admin);

        //Then
        verify(adminsRepository, times(1)).save(admin);
    }

    @Test
    void updateAdminTest() {
        //When
        doNothing().when(adminsRepository).updateAdmin(admin.getId(), admin.getFirstname(), admin.getLastname(), admin.getEmail(), admin.getUsername(), admin.getOffice(), admin.getDepartment());

        adminsService.updateAdmin(admin.getId(), admin);

        //Then
        verify(adminsRepository, times(1)).updateAdmin(admin.getId(), admin.getFirstname(), admin.getLastname(), admin.getEmail(), admin.getUsername(), admin.getOffice(), admin.getDepartment());
    }
}