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
@Table(name="Subjects")
@Data
@NoArgsConstructor
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id;
    @Column(nullable = false,unique = true)
    String name;
    @Column(nullable = true, unique = false)
    Integer year;
    @Column(nullable = true, unique = false)
    Integer semester;
    @Column(nullable=true, unique = false)
    Integer credits;
    @Column(nullable = true, unique = false)
    Integer idLectures;
    @Column(nullable = true, unique = false)
    Integer idSeminars;
    @Column(nullable = true, unique = false)
    java.util.Date createdAt;
    @Column(nullable = true, unique = false)
    java.util.Date updatedAt;
    @Column(nullable = false)
    private boolean isDeleted=false;

    public Subject(String name, Integer year, Integer semester, Integer credits, Integer idLectures, Integer idSeminars, java.util.Date createdAt, java.util.Date updatedAt) {
        this.name = name;
        this.year = year;
        this.semester = semester;
        this.credits = credits;
        this.idLectures = idLectures;
        this.idSeminars = idSeminars;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public Integer getIdLectures() {
        return idLectures;
    }

    public void setIdLectures(Integer idLectures) {
        this.idLectures = idLectures;
    }

    public Integer getIdSeminars() {
        return idSeminars;
    }

    public void setIdSeminars(Integer idSeminars) {
        this.idSeminars = idSeminars;
    }

    public java.util.Date getCreatedAt() {
        return createdAt;
    }

    public java.util.Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean getisDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}