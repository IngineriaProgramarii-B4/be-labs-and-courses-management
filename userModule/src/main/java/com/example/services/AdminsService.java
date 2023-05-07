package com.example.services;

import com.example.models.Admin;
import com.example.repository.AdminsRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class AdminsService {
    private final AdminsRepository adminsRepository;

    public AdminsService(AdminsRepository adminsRepository) {
        this.adminsRepository = adminsRepository;
    }

    public List<Admin> getAdminsByParams(Map<String, Object> params) {
        UUID id = null;
        String firstname = (String) params.get("firstname");
        String lastname = (String) params.get("lastname");
        String email = (String) params.get("email");
        String username = (String) params.get("username");
        String office = (String) params.get("office");
        String department = (String) params.get("department");

        if (params.containsKey("id") && (!params.get("id").equals(""))) {
                id = (UUID) params.get("id");

        }

        return adminsRepository.findAdminsByParams(id, firstname, lastname, email, username, office, department);
    }

    public void saveAdmin(Admin admin) {
        adminsRepository.save(admin);
    }
    @Transactional
    public void updateAdmin(UUID id, Admin admin) {
        adminsRepository.updateAdmin(id, admin.getFirstname(), admin.getLastname(), admin.getEmail(), admin.getUsername(), admin.getOffice(), admin.getDepartment());
    }
}
