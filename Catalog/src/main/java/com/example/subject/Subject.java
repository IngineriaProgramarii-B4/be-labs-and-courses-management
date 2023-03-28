package com.example.subject;

import com.example.userImpl.teacher.Teacher;

import java.util.ArrayList;
import java.util.List;

public class Subject {
    private String name;
    private List<Teacher> teachers = new ArrayList<>();

    public Subject(String name){
        this.name = name;
    }
    public Subject(String name, List<Teacher> teachers) {
        this.name = name;
        this.teachers = teachers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public void addTeacher(Teacher teacher){
        teachers.add(teacher);
    }

    @Override
    public String toString() {
        return "Subject{" +
                "name='" + name + '\'' +
                ", teachers=" + teachers +
                '}';
    }
}
