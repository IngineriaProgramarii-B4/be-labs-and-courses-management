package com.example.controllers;

import com.example.models.Admin;
import com.example.services.AdminsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("api/v1/")
public class AdminsController {
    private final AdminsService adminsService;

    @Autowired
    public AdminsController(AdminsService adminsService) {
        this.adminsService = adminsService;
    }

    @GetMapping(value = "/admins")
    public List<Admin> getAdminsByParams(@RequestParam Map<String, Object> params) {
        List<Admin> admins = adminsService.getAdminsByParams(params);

        if (admins.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return admins;
    }

    @PutMapping(value = "/admin")
    public void updateAdmin(@RequestBody Admin admin) {
        adminsService.saveAdmin(admin);
    }
}
