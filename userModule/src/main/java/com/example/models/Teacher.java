package com.example.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "teachers")
public class Teacher extends AcademicStaff {
    private Set<String> teachedSubjects = new HashSet<>();
    private String title;

    public Teacher() {

    }

    public Teacher(String firstname, String lastname, String email, String username, String office, Set<String> teachedSubjects, String title) {
        super(firstname, lastname, email, username, office, 1);
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