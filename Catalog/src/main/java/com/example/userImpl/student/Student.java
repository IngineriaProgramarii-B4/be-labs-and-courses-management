package com.example.userImpl.student;

import com.example.grades.Grade;
import com.example.user.User;
import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Entity
@Table
public class Student implements User {

    @Id
    @SequenceGenerator(
            name="student_sequence",
            sequenceName = "student_sequence",
            allocationSize=1
    )
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator="student_sequence"
    )
    private int idStud;
    private long nrMatricol;
    private String email;
    private String name;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<Grade> grades = new ArrayList<>();

    public Student(){
    }

    public Student(long nrMatricol, String email, String name) {
        this.nrMatricol = nrMatricol;
        this.email = email;
        this.name = name;
    }

    public Student(int idStud,long nrMatricol, String email, String name) {
        this.idStud = idStud;
        this.nrMatricol = nrMatricol;
        this.email = email;
        this.name = name;
    }

    public int getIdStud() {
        return idStud;
    }

    public void setIdStud(int idStud) {
        this.idStud = idStud;
    }

    public long getNrMatricol() {
        return nrMatricol;
    }

    public void setNrMatricol(long nrMatricol) {
        this.nrMatricol = nrMatricol;
    }
    public List<Grade> getGrades() {
        return grades;
    }

    public List<Grade> getGradesBySubject(String subject) {
        List<Grade> gradesList = new ArrayList<>();
        for (Grade grade : this.getGrades()) {
            if (grade.getSubject().getName().equals(subject)) {
                gradesList.add(grade);
            }
        }
        return gradesList;
    }

    public Grade getGradeById(int id){
        for(Grade grade : this.getGrades()){
            if(grade.getId()==id){
                return grade;
            }
        }
        return null;
    }
    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }
    public void addGrade(Grade grade){
        grades.add(grade);
        grade.setId(grades.size() - 1);

    }

    public void setGrade(int value,int gradeID){
        grades.get(gradeID).setValue(value);
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

    @Override
    public int getId() {
        return idStud;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student student)) return false;
        return idStud == student.idStud && nrMatricol == student.nrMatricol;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idStud, nrMatricol);
    }

    @Override
    public String toString() {
        return "Student{" +
                "idStud=" + idStud +
                ", nrMatricol=" + nrMatricol +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", grades=" + grades +
                '}';
    }
}
