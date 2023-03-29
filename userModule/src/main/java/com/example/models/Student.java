package com.example.models;

import jakarta.persistence.Entity;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Student extends AppUser {
    private  Set<String> enrolledCourses = new HashSet<>();
    private int year;
    private int semester;
    private String registrationNumber;
    /* constructor */

    public Student(int id,
                   String firstname,
                   String lastname,
                   String email,
                   String username,
                   String password,
                   int year,
                   int semester,
                   String registrationNumber,
                   Set<String> enrolledCourses) {
        super(id, firstname, lastname, email, username, password);
        this.enrolledCourses = enrolledCourses;
        this.year = year;
        this.semester = semester;
        this.registrationNumber = registrationNumber;
    }

    /* default ctor */
    public Student() {

    }

    public Set<String> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(Set<String> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public void addEnrolledCourse(String course)
    {
        enrolledCourses.add(course);
    }

    @Override
    public String toString() {
        return "Student{" +
                "enrolledCourses=" + enrolledCourses +
                ", year=" + year +
                ", semester=" + semester +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public void setElectives(Integer year, Integer semester) {

    }
}
