package com.example.controllers;

import com.example.models.Admin;
import com.example.models.User;
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
        List<Teacher> teachers = usersService.getTeachersByParams(params);

        if (teachers.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return teachers;
    }

    @GetMapping(value = "/users")
    public List<User> getUsersByParams(@RequestParam Map<String, Object> params) {
        List<User> users = usersService.getUsersByParams(params);

        if (users.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return users;
    }

    @PutMapping(value = "/student")
    public void updateStudent(@RequestBody Student student) {
        usersService.saveStudent(student);
    }

    @PutMapping(value = "/teacher")
    public void updateTeacher(@RequestBody Teacher teacher) {
        usersService.saveTeacher(teacher);
    }

    @PutMapping(value = "/admin")
    public void updateAdmin(@RequestBody Admin admin) {
        usersService.saveAdmin(admin);
    }
}