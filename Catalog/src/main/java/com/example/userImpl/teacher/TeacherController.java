package com.example.userImpl.teacher;

import com.example.userImpl.student.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/catalog/teachers")
public class TeacherController {
    private final TeacherService teacherService;
    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public List<Teacher> getTeachers(){
        return teacherService.teachersDataBase();
    }
    @GetMapping("/{id}")
    public Teacher getTeacherById(@PathVariable("id") int id){
        Optional<Teacher> teacher = Optional.ofNullable(teacherService.getTeacherById(id));
        if (teacher.isPresent()) {
            return new ResponseEntity<>(teacher.get(), HttpStatus.OK).getBody();
        } else {
            throw new NullPointerException();
        }
    }
}

