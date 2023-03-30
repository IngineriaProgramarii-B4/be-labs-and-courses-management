package com.example.catalog;

import com.example.grades.Grade;
import com.example.subject.Subject;
import com.example.userImpl.student.Student;
import com.example.userImpl.student.StudentService;
import com.example.userImpl.teacher.Teacher;
import com.example.userImpl.teacher.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatalogService {
    private final StudentService studentService;
    private final TeacherService teacherService;
    @Autowired
    public CatalogService(StudentService studentService, TeacherService teacherService) {
        this.studentService = studentService;
        this.teacherService = teacherService;
        initCatalogDB();
    }

    private final Catalog catalog = new Catalog();

    public void initCatalogDB(){

        Subject subject = new Subject("IP");
        subject.addTeacher(teacherService.getTeacherById(0));
        Subject subject1 = new Subject("PA");
        subject1.addTeacher(teacherService.getTeacherById(1));

        studentService.getStudentById(0).addGrade(new Grade(5, subject, "12.12.2004"));
        studentService.getStudentById(0).addGrade(new Grade(7,subject1, "12.12.2005"));

        studentService.getStudentById(1).addGrade(new Grade(9, subject, "08.12.2007"));
        studentService.getStudentById(1).addGrade(new Grade(4,subject1, "07.12.2009"));

        catalog.setEntries(studentService.getStudentDataBase());
    }
    public Catalog getCatalog(){
        return catalog;
    }
}
