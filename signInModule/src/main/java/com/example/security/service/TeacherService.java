package com.example.security.service;

import com.example.security.model.Teacher;
import com.example.security.repository.TeacherRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service

public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;


    public TeacherService(TeacherRepository teacherRepository, PasswordEncoder passwordEncoder) {
        this.teacherRepository = teacherRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Transactional
    public void addTeacher(Teacher teacher){
        teacherRepository.save(teacher);
    }
    @Transactional
    public void updateStudent(Long registrationNumber,String newPassword) {
        Teacher teacherToUpdate= getTeacherByRegistrationNumber(registrationNumber);
        if(teacherToUpdate != null){
            teacherToUpdate.setPassword(passwordEncoder.encode(newPassword));
            teacherRepository.save(teacherToUpdate);
            System.out.println("Student with registration number: " + teacherToUpdate.getRegistrationNumber() + " updated.");
        }
        else throw new RuntimeException("Student not found");
    }
    @Transactional
    public Teacher getTeacherByRegistrationNumber(Long registrationNumber){
        return teacherRepository.findByRegistrationNumber(registrationNumber);
    }
}
