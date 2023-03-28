package com.example.catalog;

import com.example.grades.Grade;
import com.example.subject.Subject;
import com.example.userImpl.student.Student;
import com.example.userImpl.teacher.Teacher;
import com.example.userImpl.teacher.TeacherService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CatalogService {
    public Catalog catalogDB(){
        Catalog catalog = new Catalog();
        Student student = new Student(0,301232,"student1@gmail.com", "Mihai");
        Student student1 = new Student(1,301233,"student2@gmail.com", "Andrei");

        Teacher teacher = new Teacher(0,"teacher1@gmail.com", "Ciobi");
        Teacher teacher1 = new Teacher(1,"teacher2@gmail.com", "Olariu");

        Subject subject = new Subject("IP");
        subject.addTeacher(teacher);
        Subject subject1 = new Subject("PA");
        subject1.addTeacher(teacher1);

        student.addGrade(new Grade(5, subject));
        student.addGrade(new Grade(7,subject1));

        student1.addGrade(new Grade(9, subject));
        student1.addGrade(new Grade(4,subject1));

        catalog.addEntry(student); catalog.addEntry(student1);

        return catalog;
    }
}
