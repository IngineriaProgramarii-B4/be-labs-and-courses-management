package com.example.userImpl.teacher;

import com.example.userImpl.student.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherService {
    public List<Teacher> teachersDataBase() {
        List<Teacher> teacherList = new ArrayList<>();
        Teacher teacher = new Teacher(0,"teacher1@gmail.com", "Ciobi");
        Teacher teacher1 = new Teacher(1,"teacher2@gmail.com", "Olariu");

        teacherList.add(teacher); teacherList.add(teacher1);

        return teacherList;
    }
    public Teacher getTeacherById(int id){
        return teachersDataBase().get(id);
    }
}
