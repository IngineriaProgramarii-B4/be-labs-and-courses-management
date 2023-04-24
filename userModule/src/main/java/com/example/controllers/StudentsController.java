package com.example.controllers;

import com.example.models.Student;
import com.example.services.StudentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("api/v1/")
public class StudentsController {
    private final StudentsService studentsService;

    @Autowired
    public StudentsController(StudentsService studentsService) {
        this.studentsService = studentsService;
    }

    @GetMapping(value = {"/students"})
    public List<Student> getStudentsByParams(@RequestParam Map<String, Object> params) {
        List<Student> students = studentsService.getStudentsByParams(params);

        if (students.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return students;
    }

    @PutMapping(value = "/student")
    public void updateStudent(@RequestBody Student student) {
        studentsService.saveStudent(student);
    }
}
