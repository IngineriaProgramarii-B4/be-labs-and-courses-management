package com.example.securityModule;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Entity
@Table(name = "Grades")
@Component
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id; //TODO schimbat in UUID pentru safety
    //registration number??
    @Column(nullable = false, unique = true)
    int idStudent;
    @Column(nullable = false, unique = true)
    int idLectures;
    @Column(nullable = false, unique = true)
    int idSeminars;
    @Column(nullable = false)
    int grade;
    @Column(nullable = false)
    Date date;
   /*@Column(nullable = false)
    Date createAt;

    */
    @Column(nullable = false)
    Date updatedAt;

    public Grade(int id, int idLectures, int idSeminars, int idStudent, int grade, Date date, Date updatedAt) {
        this.id = id;
        this.idLectures = idLectures;
        this.idSeminars = idSeminars;
        this.idStudent = idStudent;
        this.grade = grade;
        this.date = date;
        //this.createAt = createAt;
        this.updatedAt = updatedAt;
    }

    public Grade() {
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getGrade() {
        return grade;
    }

    public Date getDate() {
        return date;
    }



    public Date getUpdatedAt() {
        return updatedAt;
    }
}
