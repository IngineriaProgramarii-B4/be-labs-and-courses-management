package com.example.demo.services;

import com.example.demo.repositories.StudentRepository;
import com.example.demo.objects.Student;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    @Transactional
    public void addStudent(Student student) {
        //if student already exists throw error
        student.hashPassword();
        studentRepository.save(student);
    }
    @Transactional
    public void removeStudent(Student student) {
        Student isStudent = studentRepository.findByRegistrationNumber(student.getRegistrationNumber());
        if(isStudent != null) {
            studentRepository.delete(isStudent);
            System.out.println("Student removed... " + isStudent.getLastName());
        }
        else
            throw new RuntimeException("Can't remove non-existent student...");
    }

    @Transactional
    public void updateStudent(Student updatedStudent) {
        Student studentToUpdate= getStudentByRegistrationNumber(updatedStudent.getRegistrationNumber());
        if(studentToUpdate != null){
            studentToUpdate.setYear(updatedStudent.getYear());
            studentToUpdate.setGrupa(updatedStudent.getGrupa());
            studentToUpdate.setLastName(updatedStudent.getLastName());
            studentRepository.save(studentToUpdate);
            System.out.println("Student with registration number: " + updatedStudent.getRegistrationNumber() + " updated.");
        }
        else throw new RuntimeException("Student not found");
        }



    @Transactional
    public Student getStudentByRegistrationNumber(String registrationNumber){
        return studentRepository.findByRegistrationNumber(registrationNumber);
    }

    @Transactional
    public List<Student> getStudentByYear(int year){
        return studentRepository.findByYear(year);
    }

    @Transactional
    public List<Student> getStudentByGrupaAndYear(String grupa, int year) {
        return studentRepository.findByGrupaAndYear(grupa, year);
    }
    @Transactional
    public List<Student> getStudentByFirstName(String firstName){
        return studentRepository.findByFirstName(firstName);
    }
    @Transactional
    public List<Student> getStudentByLastName(String lastName){
        return studentRepository.findByLastName(lastName);
    }
}
