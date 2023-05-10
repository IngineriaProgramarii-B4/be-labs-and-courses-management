package com.example.demo.services;

import com.example.demo.objects.Student;

import com.example.demo.repositories.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private List<Student> students=new ArrayList<>();

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
        updateStudents();
    }
    public void updateStudents(){
        students=studentRepository.findAll();
    }
    @Transactional
    public void addStudent(String registrationNumber, String firstName, String lastName,int year, String grupa, String mail, String password) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        Date createdAt= new Date(formatter.format(date));
        Date updatedAt= new Date(formatter.format(date));
        Student student = new Student(registrationNumber,firstName,lastName,year,grupa,mail,password,createdAt,updatedAt);

        student.hashPassword();
        students.add(student);
        studentRepository.save(student);
    }
    @Transactional
    public void deleteStudent(Student student) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        studentRepository.findById(student.getId()).ifPresent(a1->{ a1.setDeleted(true);a1.setUpdatedAt(new Date(formatter.format(new Date())));
            studentRepository.save(a1);});
    }

    @Transactional
    public void updateStudent(Student student, String newFirstName, String newLastName) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        studentRepository.findById(student.getId()).ifPresent(a1->{ a1.setFirstName(newFirstName);a1.setLastName(newLastName);a1.setUpdatedAt(new Date(formatter.format(new Date())));
            studentRepository.save(a1);});
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