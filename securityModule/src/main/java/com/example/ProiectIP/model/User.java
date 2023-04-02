package com.example.ProiectIP.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity ////!!!Atentie posibil pe viitor sa adaugam persoane si profesori
@Table(name = "Users")
public class User {
    int id; //TODO schimbat in UUID pentru safety
    String mail;
    String password;
    String firstName;
    String lastName;
    //poate roluri pt diferite materii in functie de an si semestru?

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    public User(int id, String mail, String password, String firstName, String lastName, String resetPasswordToken) {
        this.id = id;
        this.mail = mail;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.resetPasswordToken = resetPasswordToken;
    }

    public User() {}

    public void setResetPasswordToken(String token) {
        this.resetPasswordToken = token;
    }

    public void setPassword(String encodedPassword) {
        this.password = password;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}

