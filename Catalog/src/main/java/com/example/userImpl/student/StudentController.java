package com.example.userImpl.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.rmi.ServerException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/catalog/students")
public class StudentController {
    private final StudentService studentService;
    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @GetMapping
    public List<Student> getStudents(){
        return studentService.getStudentDataBase();
    }
    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable("id") int id) {
        Optional<Student> student = Optional.ofNullable(studentService.getStudentById(id));
        if (student.isPresent()) {
            return new ResponseEntity<>(student.get(), HttpStatus.OK).getBody();
        } else {
            throw new NullPointerException();
        }
    }
    @PostMapping(path = "",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> create(@RequestBody Student newStudent) throws ServerException {

        Student user = studentService.save(newStudent);
        if (user == null) {
            throw new ServerException("Cannot save.");
        } else {
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Student> delete(@PathVariable("id") int id){
        Student isRemoved = studentService.delete(getStudentById(id));
        if (isRemoved == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(isRemoved, HttpStatus.OK);
    }

}
