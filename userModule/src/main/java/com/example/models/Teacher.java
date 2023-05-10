package com.example.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "teachers")
public class Teacher extends AcademicStaff {
    private Set<String> taughtSubjects = new HashSet<>();
    private String title;

    public Teacher() {

    }

    public Teacher(UUID id, String firstname, String lastname, String email, String username, String office, Set<String> teachedSubjects, String title) {
        super(id, firstname, lastname, email, username, office, 1);
        this.taughtSubjects = teachedSubjects;
        this.title = title;
    }
    public Teacher(String firstname, String lastname, String email, String username, String office, Set<String> teachedSubjects, String title) {
        super(firstname, lastname, email, username, office, 1);
        this.taughtSubjects = teachedSubjects;
        this.title = title;
    }

    public Set<String> getTaughtSubjects() {
        return taughtSubjects;
    }

    public void setTaughtSubjects(Set<String> taughtSubjects) {
        this.taughtSubjects = taughtSubjects;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Teacher{" + "teachedSubjects=" + taughtSubjects + ", title='" + title + '\'' + ", office='" + office + '\'' + ", id=" + id + ", firstname='" + firstname + '\'' + ", lastname='" + lastname + '\'' + ", email='" + email + '\'' + ", username='" + username + '\'' + '}';
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object user) {
        return super.equals(user);
    }
}