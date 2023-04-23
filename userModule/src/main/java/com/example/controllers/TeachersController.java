package com.example.controllers;

import com.example.models.Teacher;
import com.example.services.TeachersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("api/v1/")
public class TeachersController {
    private final TeachersService teachersService;

    @Autowired
    public TeachersController(TeachersService teachersService) {
        this.teachersService = teachersService;
    }

    @GetMapping(value = "/teachers")
    public List<Teacher> getTeachersByParams(@RequestParam Map<String, Object> params) {
        List<Teacher> teachers = teachersService.getTeachersByParams(params);

        if (teachers.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return teachers;
    }

    @PutMapping(value = "/teacher")
    public void updateTeacher(@RequestBody Teacher teacher) {
        teachersService.saveTeacher(teacher);
    }
}
