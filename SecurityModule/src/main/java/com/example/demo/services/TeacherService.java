package com.example.demo.services;

import com.example.demo.objects.Teacher;
import com.example.demo.repositories.TeacherRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;
    @Autowired
    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Transactional
    public void addTeacher(Teacher teacher){
        teacherRepository.save(teacher);
    }
    @Transactional
    public void deleteTeacher(String mail){
       Teacher teacherToDelete = teacherRepository.findByMail(mail);
       teacherRepository.delete(teacherToDelete);
    }
    @Transactional
    public void updateTeacher(String mail, String newFirstName, String newLastName, String newDegree){
                Teacher teacherToUpdate = teacherRepository.findByMail(mail);
                if(teacherToUpdate != null){
                teacherToUpdate.setFirstName(newFirstName);
                teacherToUpdate.setLastName(newLastName);
                teacherToUpdate.setDegree(newDegree);
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();
                Date updatedAt= new Date(formatter.format(date));
                teacherToUpdate.setUpdatedAt(updatedAt);
                teacherRepository.save(teacherToUpdate);
            }
                else throw new RuntimeException("Teacher not found.");
        }

    @Transactional
    public List<Teacher> getTeacherByLastName(String lastName){
        return teacherRepository.findByLastName(lastName);
    }
    @Transactional
    public List<Teacher> getTeacherByDegree(String degree){
        return teacherRepository.findByDegree(degree);
    }
    @Transactional
    public Teacher getTeacherByMail(String mail){
       return teacherRepository.findByMail(mail);
    }
}