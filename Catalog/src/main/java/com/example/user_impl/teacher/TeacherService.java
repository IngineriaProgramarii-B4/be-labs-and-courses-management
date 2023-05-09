package com.example.user_impl.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository){
        this.teacherRepository=teacherRepository;
    }

    public List<Teacher> getTeacherDataBase(){
        return teacherRepository.getAllTeachers();
    }
    public Teacher getTeacherById(int id){
        return this.teacherRepository.getTeacherById(id).orElseThrow(() -> new IllegalStateException("Teacher with id "+id+" doesn't exist."));
    }

    public Teacher save(Teacher teacher) {
        teacherRepository.save(teacher);
        return teacher;
    }

    public Teacher delete(Teacher teacher){
        try {
            teacherRepository.delete(teacher);
            return teacher;
        }
        catch (NullPointerException e) {
            return null;
        }
    }
}
