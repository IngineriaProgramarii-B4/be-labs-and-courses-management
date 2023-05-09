package com.example.demo.objects;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Entity
@Component
@Table(name = "Users")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id; //TODO schimbat integer -> UUID pentru safety

    @Column(nullable = false)
    String firstName;
    @Column(nullable = false)
    String lastName;
    @Column(nullable = false, unique = true)
    String mail;
    @Column(nullable = false, unique = true)
    String password;
    //@Column(name = "reset_password_token")
    //private String resetPasswordToken;
    @Column(nullable = false)
    private boolean isDeleted=false;
    public User(String firstName, String lastName, String mail, String password) {
        this.mail = mail;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    /*
    public void setResetPasswordToken(String token) {
        this.resetPasswordToken = token;
    }

     */
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

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}