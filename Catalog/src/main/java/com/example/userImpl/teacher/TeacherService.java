package com.example.userImpl.teacher;

import com.example.userImpl.student.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        Teacher teacher=(Teacher)this.teacherRepository.getTeacherById(id).orElseThrow(() -> new IllegalStateException("Teacher with id "+id+" doesn't exist."));
        return teacher;
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
