package com.example.subject;

import com.example.userImpl.teacher.Teacher;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Subject {

    @Id
    private String name;

    @OneToMany(cascade = {CascadeType.MERGE})
    private List<Teacher> teachers = new ArrayList<>();

    public Subject(){

    }

    public Subject(String name){
        this.name = name;
    }
    public Subject(String name, List<Teacher> teachers) {
        this.name = name;
        this.teachers = teachers;
    }

//    public Subject(String name,
//                   @JsonProperty("teachers") List<String> teacherList){
//        List<Teacher> list=new ArrayList<>();
//        for(int i=0;i<teacherList.size();i++){
//            for(Teacher teacherIterator : teachers){
//                if(teacherIterator.getName()==teacherList.get(i)){
//
//                }
//            }
//        }
//    }

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
