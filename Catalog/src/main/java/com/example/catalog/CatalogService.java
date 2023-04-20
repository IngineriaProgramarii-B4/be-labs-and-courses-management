package com.example.catalog;

import com.example.grades.Grade;
import com.example.grades.GradeService;
import com.example.subject.Subject;
import com.example.userImpl.student.Student;
import com.example.userImpl.student.StudentService;
import com.example.userImpl.teacher.Teacher;
import com.example.userImpl.teacher.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogService {
    private final StudentService studentService;
    private final TeacherService teacherService;

    private final GradeService gradeService;

    private final Catalog catalog = new Catalog();

    @Autowired
    public CatalogService(StudentService studentService, TeacherService teacherService,GradeService gradeService) {
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.gradeService = gradeService;
        initCatalogDB();
    }

    public void initCatalogDB(){

//        Subject subject = new Subject("IP");
//        subject.addTeacher(teacherService.getTeacherById(0));
//        Subject subject1 = new Subject("PA");
//        subject1.addTeacher(teacherService.getTeacherById(1));

        catalog.setEntries(studentService.getStudentDataBase());
    }
    public List<Student> getCatalog(){
        return studentService.getStudentDataBase();
    }
}
