package com.example.demo.objects;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Component
@Table(name="Teachers")
@Data
@NoArgsConstructor
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id;
    @Column(nullable = false)
    String firstName;
    @Column(nullable = false)
    String lastName;
    @Column(nullable = false)
    String degree;
    @Column(nullable = false, unique = true)
    String mail;
    @Column(nullable = false)
    String password;
    @Column(nullable = true)
    java.util.Date createdAt;
    @Column(nullable = true)
    java.util.Date updatedAt;
    @Column(nullable = false, unique = true)
    String registrationNumber; //adaugat in constructor

    public Teacher(String firstName, String lastName, String degree,String mail, String password, String registrationNumber){
        this.id = UUID.randomUUID();
        this.registrationNumber=registrationNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.degree = degree;
        this.mail = mail;
        this.password = password;
    }

    public Teacher(UUID id, String firstName, String lastName, String mail, String password, String degree, Date createdAt, Date updatedAt) {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void hashPassword() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }
    public void setPassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDegree() {
        return degree;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }
    public java.util.Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public java.util.Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}