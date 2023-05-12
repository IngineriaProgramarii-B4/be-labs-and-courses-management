package com.example.demo.services;

import com.example.demo.objects.Teacher;
import com.example.demo.repositories.TeacherRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private List<Teacher> teachers = new ArrayList<>();

    @Autowired
    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
        updateTeachers();
    }

    public void updateTeachers() {
        this.teachers = teacherRepository.findAll();
    }

    @Transactional
    public void addTeacher(String firstName, String lastName, String degree, String mail, String password) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        java.util.Date date = new java.util.Date();
        java.util.Date createdAt = new java.util.Date(formatter.format(date));
        java.util.Date updatedAt = new java.util.Date(formatter.format(date));
        Teacher teacher = new Teacher(firstName, lastName, mail, password, degree, createdAt, updatedAt);
        teacher.hashPassword();
        teachers.add(teacher);
        teacherRepository.save(teacher);
    }

    @Transactional
    public void deleteTeacher(Teacher teacher1){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        teacherRepository.findById(teacher1.getId()).ifPresent(a1->{ a1.setDeleted(true);a1.setUpdatedAt(new Date(formatter.format(new Date())));
            teacherRepository.save(a1);});
    }

    @Transactional
    public void updateTeacher(Teacher teacher1, String newFirstName, String newLastName){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        teacherRepository.findById(teacher1.getId()).ifPresent(a1->{ a1.setFirstName(newFirstName); a1.setLastName(newLastName); a1.setUpdatedAt(new Date(formatter.format(new Date())));
            teacherRepository.save(a1);});
    }

    public List<Teacher> getTeacherByLastName(String lastName) {
        return teacherRepository.findByLastName(lastName);
    }

    @Transactional
    public List<Teacher> getTeacherByDegree(String degree) {
        return teacherRepository.findByDegree(degree);
    }

    @Transactional
    public Teacher getTeacherByMail(String mail) {
        return teacherRepository.findByMail(mail);
    }
}