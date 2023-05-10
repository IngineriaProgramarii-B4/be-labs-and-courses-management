package com.example.security.model;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.persistence.FetchType;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Students")
@Component
@Data
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id;
    Long registrationNumber;
    String firstName;
    String lastName;
    Integer year;
    String grupa;
    @Column( unique = true)
    String mail;
    String password;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "registrationNumber"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles = new ArrayList<>();

    public Student(Long registrationNumber, String firstName, String lastName, int year, String grupa, String mail, String password) {
        this.registrationNumber = registrationNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.year = year;
        this.grupa = grupa;
        this.mail = mail;
        this.password = password;
    }

    public void hashPassword() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    public UUID getId() {
        return id;
    }

    public Long getRegistrationNumber() {
        return registrationNumber;
    }

}