package com.example.user_impl.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.rmi.ServerException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/catalog/teachers")
@CrossOrigin(origins = "*")
public class TeacherController {
    private final TeacherService teacherService;
    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    @CrossOrigin(origins = "*")
    public List<Teacher> getTeachers(){
        return teacherService.getTeacherDataBase();
    }
    @GetMapping("/{id}")
    @CrossOrigin(origins = "*")
    public Teacher getTeacherById(@PathVariable("id") int id){
        Optional<Teacher> teacher = Optional.ofNullable(teacherService.getTeacherById(id));
        if (teacher.isPresent()) {
            return new ResponseEntity<>(teacher.get(), HttpStatus.OK).getBody();
        } else {
            throw new NullPointerException();
        }
    }
    @PostMapping(path = "",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Teacher> create(@RequestBody Teacher newTeacher) throws ServerException {

        Teacher user = teacherService.save(newTeacher);
        if (user == null) {
            throw new ServerException("Cannot save.");
        } else {
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Teacher> delete(@PathVariable("id") int id){
        Teacher isRemoved = teacherService.delete(getTeacherById(id));
        if (isRemoved == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(isRemoved, HttpStatus.OK);
    }
}

