package com.example.userImpl.student;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class StudentService {

    private final List<Student> studentList = new ArrayList<>();

    public StudentService(){
        initStudentDataBase();
    }
    public List<Student> initStudentDataBase() {

        Student student = new Student(0,301232,"student1@gmail.com", "Mihai");
        Student student1 = new Student(1,301233,"student2@gmail.com", "Andrei");
        studentList.add(student); studentList.add(student1);

        return studentList;
    }

    public List<Student> getStudentDataBase(){
        return studentList;
    }
    public Student getStudentById(int id){
        return studentList.get(id);
    }
    public Student save(Student student) {
        studentList.add(student);
        return student;
    }

    public Student delete(Student student){
        try {
            Student deleted = student;
            studentList.remove(student);
            return deleted;
        }
        catch (NullPointerException e) {
            return null;
        }
    }
}
