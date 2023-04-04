package com.example.models;

import jakarta.persistence.Entity;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Teacher extends AcademicStaff {
    private Set<String> teachedSubjects = new HashSet<>();
    private String title;

    public Teacher() {

    }

    public Teacher(int id, String firstname, String lastname, String email, String username, String office, Set<String> teachedSubjects, String title) {
        super(id, firstname, lastname, email, username, office);
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
        return "Teacher{" + "teachedSubjects=" + teachedSubjects + ", title='" + title + '\'' + ", office='" + office + '\'' + ", id=" + id + ", firstname='" + firstname + '\'' + ", lastname='" + lastname + '\'' + ", email='" + email + '\'' + ", username='" + username + '\'' + '}';
    }

    public void manageEvaluationSystem() {

    }
}