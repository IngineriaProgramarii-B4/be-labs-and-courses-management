package com.example.userImpl.teacher;

import com.example.subject.Subject;
import com.example.user.User;

import java.util.List;
import java.util.Objects;

public class Teacher implements User {
    private int idProf;
    private String email;
    private String name;
    private List<Subject> teachedSubjects;

    public Teacher(int idProf, String email, String name) {
        this.idProf = idProf;
        this.email = email;
        this.name = name;
    }

    public int getIdProf() {
        return idProf;
    }

    public void setIdProf(int idProf) {
        this.idProf = idProf;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public List<Subject> getTeachedSubjects() {
        return teachedSubjects;
    }

    public void setTeachedSubjects(List<Subject> teachedSubjects) {
        this.teachedSubjects = teachedSubjects;
    }
    @Override
    public int getId() {
        return idProf;
    }
    public void addSubject(Subject subject){
        teachedSubjects.add(subject);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Teacher teacher)) return false;
        return idProf == teacher.idProf && Objects.equals(email, teacher.email);
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "idProf=" + idProf +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", teachedSubjects=" + teachedSubjects +
                '}';
    }
}
