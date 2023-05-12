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
    private UUID id;

    @Column(nullable = false, unique = false)
    private UUID idStudent;

    @Column(nullable = false, unique = false)
    private UUID idLecture;

    @Column(nullable = false, unique = false)
    private UUID idSeminar;
    @Column(nullable = false)
    double value;
    @Column(nullable = false)
    Date date;
    @Column(nullable=false)
    private Date createAt;
    @Column(nullable = false)
    private Date updatedAt;
    @Column(nullable = false)
    private boolean isDeleted=false;

    public Grade(UUID idStudent, UUID idLecture, UUID idSeminar, double value, Date date, Date createAt, Date updatedAt) {
        this.idStudent = idStudent;
        this.idLecture = idLecture;
        this.idSeminar = idSeminar;
        if(value >=1 && value <=10)
            this.value = value;
        else throw new IllegalArgumentException("Nu se poate insera nota");
        this.date = date;
        this.createAt = createAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public UUID getIdLecture() {
        return idLecture;
    }

    public void setIdLecture(UUID idLecture) {
        this.idLecture = idLecture;
    }

    public UUID getIdSeminar() {
        return idSeminar;
    }

    public void setIdSeminar(UUID idSeminar) {
        this.idSeminar = idSeminar;
    }

    public void setIdStudent(UUID idStudent) {
        this.idStudent = idStudent;
    }

    public void setValue(double value) {
        if(value >=1 && value <=10)
            this.value = value;
        else throw new IllegalArgumentException("Nu se poate insera nota");
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

    public double getValue() {
        return value;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}