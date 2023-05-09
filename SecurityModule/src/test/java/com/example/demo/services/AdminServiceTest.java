package com.example.demo.services;

import com.example.demo.objects.Admin;
import com.example.demo.repositories.AdminRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class AdminServiceTest {
    private final AdminRepository adminRepository = Mockito.mock(AdminRepository.class);
    private AdminService adminService = new AdminService(adminRepository);

    @Test
    public void testAddAdmins() {
        // Given
        UUID id = UUID.randomUUID();
        String name = "John";
        String surname = "Doe";

        // When
        adminService.addAdmins(id, name, surname);

        // Then
        Mockito.verify(adminRepository, times(1)).save(any(Admin.class));
    }
    @Test
    public void testDeleteAdmins() {
        UUID id = UUID.randomUUID();
        String name = "John";
        String surname = "Doe";
        Admin admin = new Admin(id, name, surname, null, null);

        List<Admin> adminList = new ArrayList<>();
        adminList.add(admin);

        when(adminRepository.findAll()).thenReturn(adminList);

        AdminService adminService = new AdminService(adminRepository);
        adminService.deleteAdmins(name, surname);

        assertEquals(0, adminList.size());
    }

    @Test
    public void testGetAdminByName() {
        // Create a list of admins for testing
        List<Admin> admins = new ArrayList<>();
        UUID id = UUID.randomUUID();
        String name = "John";
        String surname = "Doe";
        Admin admin = new Admin(id, name, surname, null, null);
        admins.add(admin);

        // Mock the behavior of the repository
        when(adminRepository.findByName(name)).thenReturn(admins);

        // Create an instance of the service and call the method being tested
        AdminService adminService = new AdminService(adminRepository);
        List<Admin> result = adminService.getAdminByName(name);

        // Check that the result is correct
        assertEquals(admins, result);
    }

    @Test
    public void testGetAdminBySurname() {
        List<Admin> admins = new ArrayList<>();
        UUID id = UUID.randomUUID();
        String name = "John";
        String surname = "Doe";
        Admin admin = new Admin(id, name, surname, null, null);
        admins.add(admin);

        when(adminRepository.findBySurname(surname)).thenReturn(admins);

        AdminService adminService = new AdminService(adminRepository);
        List<Admin> result = adminService.getAdminBySurname(surname);

        assertEquals(admins, result);
    }

    @Test
    public void testGetAdminByNameAndSurname() {
        // Create a sample admin object
        UUID adminId = UUID.randomUUID();
        String adminName = "John";
        String adminSurname = "Doe";
        Admin sampleAdmin = new Admin(adminId, adminName, adminSurname, null, null);

        // Set up the mock repository to return the sample admin object
        when(adminRepository.findByNameAndSurname(adminName, adminSurname)).thenReturn(sampleAdmin);

        // Call the method being tested
        Admin returnedAdmin = adminService.getAdminByNameAndSurname(adminName, adminSurname);

        // Verify the result
        assertEquals(sampleAdmin, returnedAdmin);
    }


}