package com.example.services;

import com.example.models.Admin;
import com.example.repository.AdminsRepository;
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

        if (params.containsKey("id")) {
            if (!params.get("id").equals("")) {
                id = UUID.fromString((String) params.get("id"));
            }
        }

        List<Admin> admins = adminsRepository.findAdminsByParams(id, firstname, lastname, email, username, office, department);

        return admins;
    }

    public void saveAdmin(Admin admin) {
        adminsRepository.save(admin);
    }
}
