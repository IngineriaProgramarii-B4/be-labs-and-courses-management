package com.example.userImpl.teacher;

import com.example.userImpl.student.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherService {
    private final List<Teacher> teacherList = new ArrayList<>();

    public TeacherService(){
        initTeachersDataBase();
    }
    public void initTeachersDataBase() {
        Teacher teacher = new Teacher(0,"teacher1@gmail.com", "Ciobi");
        Teacher teacher1 = new Teacher(1,"teacher2@gmail.com", "Olariu");
        teacherList.add(teacher); teacherList.add(teacher1);
    }
    public List<Teacher> getTeacherDataBase(){
        return teacherList;
    }
    public Teacher getTeacherById(int id){
        return teacherList.get(id);
    }

    public Teacher save(Teacher teacher) {
        teacherList.add(teacher);
        return teacher;
    }

    public Teacher delete(Teacher teacher){
        try {
            teacherList.remove(teacher);
            return teacher;
        }
        catch (NullPointerException e) {
            return null;
        }
    }
}
