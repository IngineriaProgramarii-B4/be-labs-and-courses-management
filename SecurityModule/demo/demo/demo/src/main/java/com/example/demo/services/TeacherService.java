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
    private List<Teacher> teachers=new ArrayList<>();
    @Autowired
    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
        updateTeachers();
    }
    public void updateTeachers(){this.teachers=teacherRepository.findAll();
    }

    @Transactional
    public void addTeacher(String firstName, String lastName, String degree, String mail, String password){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        java.util.Date date = new java.util.Date();
        java.util.Date createdAt= new java.util.Date(formatter.format(date));
        java.util.Date updatedAt= new java.util.Date(formatter.format(date));
        Teacher teacher = new Teacher(firstName, lastName, mail, password, degree, createdAt, updatedAt);
        teacher.hashPassword();
        teachers.add(teacher);
        teacherRepository.save(teacher);
    }
    @Transactional
    public void deleteTeacher(String name){
        for (Teacher teacher_iterator : teachers) {
            if (teacher_iterator.getFirstName().equals(name)) {
                //teachers.remove(teacher_iterator);
                //teacherRepository.delete(teacher_iterator);
                String mail = teacher_iterator.getMail();
                updateTeacherIsDeleted(mail);
                break;
            }
        }
    }
    @Transactional
    public void updateTeacher(String mail, String newFirstName, String newLastName, String newDegree){
        updateTeachers();
        Teacher newTeacher= new Teacher();
        for (Teacher teacher_iterator : teachers) {
            if (teacher_iterator.getMail().equals(mail) && !teacher_iterator.getIsDeleted()) {
                Optional<Teacher> newTeacherOp=teacherRepository.findById(teacher_iterator.getId());
                if(newTeacherOp.isPresent()){
                    newTeacher=newTeacherOp.get();
                }
                newTeacher.setFirstName(newFirstName);
                newTeacher.setLastName(newLastName);
                newTeacher.setDegree(newDegree);
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();
                Date updatedAt= new Date(formatter.format(date));
                newTeacher.setUpdatedAt(updatedAt);
                teachers.remove(teacher_iterator);
                teachers.add(newTeacher);
                teacherRepository.save(newTeacher);
                break;
            }
        }
        System.out.println("Nu s-a gasit niciun obiect de modificat");
    }

    @Transactional
    public void updateTeacherIsDeleted(String mail){
        updateTeachers();
        Teacher newTeacher= new Teacher();
        for (Teacher teacher_iterator : teachers) {
            if (teacher_iterator.getMail().equals(mail) && !teacher_iterator.getIsDeleted()) {
                Optional<Teacher> newTeacherOp=teacherRepository.findById(teacher_iterator.getId());
                if(newTeacherOp.isPresent()){
                    newTeacher=newTeacherOp.get();
                }
                newTeacher.setDeleted(true);
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                java.util.Date date = new java.util.Date();
                java.util.Date updatedAt= new java.util.Date(formatter.format(date));
                newTeacher.setUpdatedAt(updatedAt);
                teachers.remove(teacher_iterator);
                teachers.add(newTeacher);
                teacherRepository.save(newTeacher);
                break;
            }
        }

    }
    @Transactional
    public List<Teacher> getTeacherByName(String lastName){
        List<Teacher> teachersInName=new ArrayList<>();
        for (Teacher teacher_iterator : teachers) {
            if (teacher_iterator.getLastName().equals(lastName)) {
                teachersInName.add(teacher_iterator);
            }
        }
        return teachersInName;
    }
    @Transactional
    public List<Teacher> getTeacherByDegree(String degree){
        List<Teacher> teachersInDegree=new ArrayList<>();
        for (Teacher teacher_iterator : teachers) {
            if (teacher_iterator.getDegree().equals(degree)) {
                teachersInDegree.add(teacher_iterator);
            }
        }
        return teachersInDegree;
    }
    @Transactional
    public Teacher getTeacherByMail(String mail){
        for (Teacher teacher_iterator : teachers) {
            if (teacher_iterator.getMail().equals(mail)) {
                return teacher_iterator;
            }
        }
        return null;
    }
}
