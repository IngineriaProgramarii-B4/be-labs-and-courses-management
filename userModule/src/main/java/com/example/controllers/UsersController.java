package com.example.controllers;

import com.example.models.Admin;
import com.example.models.AppUser;
import com.example.models.Student;
import com.example.models.Teacher;
import com.example.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("api/v1/")
public class UsersController {
    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping(value = {"/students"})
    public List<Student> getStudentsByParams(@RequestParam Map<String, Object> params) {
        List<Student> students = usersService.getStudentsByParams(params);

        if (students.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        System.out.println(students.get(0));
        return students;
    }

    @GetMapping(value = "/admins")
    public List<Admin> getAdminsByParams(@RequestParam Map<String, Object> params) {
        List<Admin> admins = usersService.getAdminsByParams(params);

        if (admins.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return admins;
    }

    @GetMapping(value = "/teachers")
    public List<Teacher> getProfessorsByParams(@RequestParam Map<String, Object> params) {
        List<Teacher> professors = usersService.getProfessorByParams(params);

        if (professors.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return professors;
    }

    @GetMapping(value = "/users")
    public List<AppUser> getUsersByParams(@RequestParam Map<String, Object> params) {
        List<AppUser> users = usersService.getUsersByParams(params);

        if (users.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return users;
    }

    @PostMapping(value = "/updated/student")
    public void updateUser(@RequestBody Student student) {
        usersService.saveStudent(student);
    }
}
