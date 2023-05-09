package com.example.demo.objects;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Entity
@Component
@Table(name="Grades")
@Data
@NoArgsConstructor
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id; //TODO schimbat integer -> UUID pentru safety

    @Column(nullable = false, unique = false)
    private UUID idStudent;

    @Column(nullable = false, unique = false)
    private UUID idLectures;

    @Column(nullable = false, unique = false)
    private UUID idSeminars;
    @Column(nullable = false)
    double grade;
    @Column(nullable = false)
    Date date;
    @Column(nullable=false)
    private Date createAt;
    @Column(nullable = false)
    private Date updatedAt;

    public Grade(UUID idStudent, UUID idLectures, UUID idSeminars,double grade, Date date, Date createAt, Date updatedAt) {
        this.idStudent = idStudent;
        this.idLectures = idLectures;
        this.idSeminars = idSeminars;
        if(grade>=1 && grade<=10)
            this.grade = grade;
        this.date = date;
        this.createAt = createAt;
        this.updatedAt = updatedAt;
    }

    public void setGrade(double grade) {
        if(grade>=1 && grade<=10)
            this.grade = grade;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UUID getIdStudent() {
        return idStudent;
    }

    public Date getDate() {
        return date;
    }

    public double getGrade() {
        return grade;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }
}