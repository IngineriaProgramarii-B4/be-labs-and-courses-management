package com.example.models;

import java.util.HashSet;
import java.util.Set;

//@Entity
//@Tabel
public class Professor extends AcademicStaff {
    private Set<String> teachedSubjects = new HashSet<>();
    private String title;

    public Professor () {

    }

    public Professor(int id,
                     String firstname,
                     String lastname,
                     String email,
                     String username,
                     String password,
                     String office,
                     Set<String> teachedSubjects,
                     String title) {
        super(id, firstname, lastname, email, username, password, office);
        this.teachedSubjects = teachedSubjects;
        this.title = title;
    }

    public Set<String> getTeachedSubjects() {
        return teachedSubjects;
    }

    public void setTeachedSubjects(Set<String> teachedSubjects) {
        this.teachedSubjects = teachedSubjects;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Professor{" +
                "teachedSubjects=" + teachedSubjects +
                ", title='" + title + '\'' +
                ", office='" + office + '\'' +
                ", id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public void manageEvaluationSystem() {

    }
}
