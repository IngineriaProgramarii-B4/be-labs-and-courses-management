package com.example.security.service;

import com.example.security.exception.StudentNotFoundException;
import com.example.security.model.Student;
import com.example.security.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    public StudentService(StudentRepository studentRepository, PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public void addStudent(Student student) {
        //if student already exists throw error
        studentRepository.save(student);
    }
    @Transactional
    public void updateStudent(Long registrationNumber,String newPassword) {
        Student studentToUpdate= getStudentByRegistrationNumber(registrationNumber);
        if(studentToUpdate != null){
            studentToUpdate.setPassword(passwordEncoder.encode(newPassword));
            studentRepository.save(studentToUpdate);
        }
        throw new StudentNotFoundException("Student not found with registration number: " + registrationNumber);
    }
    @Transactional
    public Student getStudentByRegistrationNumber(Long registrationNumber){
        return studentRepository.findByRegistrationNumber(registrationNumber);
    }
}
