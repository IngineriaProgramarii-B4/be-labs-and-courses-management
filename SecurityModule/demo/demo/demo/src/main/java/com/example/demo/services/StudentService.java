package com.example.demo.services;

import com.example.demo.objects.Student;
import com.example.demo.objects.Subject;
import com.example.demo.repositories.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private List<Student> students=new ArrayList<>();
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
        //if student already exists throw error
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        java.util.Date date = new java.util.Date();
        java.util.Date createdAt= new java.util.Date(formatter.format(date));
        java.util.Date updatedAt= new java.util.Date(formatter.format(date));
        Student student = new Student(registrationNumber,firstName,lastName,year,grupa,mail,password,createdAt,updatedAt);

        student.hashPassword();
        students.add(student);
        studentRepository.save(student);
    }
    @Transactional
    public void deleteStudent(String registrationNumber) {
        for(Student student_it : students){
            if(student_it.getRegistrationNumber().equals(registrationNumber)){
                //studentRepository.delete(student_it);
                updateStudentIsDeleted(registrationNumber);
                break;
            }
        }
    }

    @Transactional
    public void updateStudent(String registrationNumber, String newFirstName, String newLastName, Integer newYear, String newGrupa, String newMail, String newPassword) {
        updateStudents();
        Student newStudent=new Student();
        for(Student student_it : students) {
            if (student_it.getRegistrationNumber().equals(registrationNumber) && !student_it.getIsDeleted()) {
                System.out.println(student_it.getId() + "STUDENT");
                Optional<Student> newStudentOp = studentRepository.findById(student_it.getId());
                if(newStudentOp.isPresent()){
                    newStudent=newStudentOp.get();
                }
                newStudent.setFirstName(newFirstName);
                newStudent.setLastName(newLastName);
                newStudent.setYear(newYear);
                newStudent.setGrupa(newGrupa);
                newStudent.setMail(newMail);
                newStudent.setPassword(newPassword);
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                java.util.Date date = new java.util.Date();
                java.util.Date updatedAt = new java.util.Date(formatter.format(date));
                newStudent.setUpdatedAt(updatedAt);
                students.remove(student_it);
                students.add(newStudent);
                studentRepository.save(newStudent);
                break;
            }
        }
        System.out.println("Nu s-a gasit niciun obiect de modificat");
    }
    @Transactional
    public void updateStudentIsDeleted(String registrationNumber) {
        updateStudents();
        Student newStudent=new Student();
        for(Student student_it : students) {
            if (student_it.getRegistrationNumber().equals(registrationNumber)) {
                System.out.println(student_it.getId() + "STUDENT");
                Optional<Student> newStudentOp = studentRepository.findById(student_it.getId());
                if(newStudentOp.isPresent()){
                    newStudent=newStudentOp.get();
                }
                newStudent.setDeleted(true);

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();
                Date updatedAt = new Date(formatter.format(date));
                newStudent.setUpdatedAt(updatedAt);
                students.remove(student_it);
                students.add(newStudent);
                studentRepository.save(newStudent);
                break;
            }
        }
    }

    @Transactional
    public Student getStudentByRegistrationNumber(String registrationNumber){
        for (Student student_it : students){
            if(student_it.getRegistrationNumber().equals(registrationNumber)){
                return student_it;
            }
        }
        return null;
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
