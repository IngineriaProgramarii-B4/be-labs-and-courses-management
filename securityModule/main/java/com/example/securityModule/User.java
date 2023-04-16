package com.example.securityModule;
import jakarta.persistence.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "Users")
@Component
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id; //TODO schimbat in UUID pentru safety
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
    public User(int id, String firstName, String lastName, String mail, String password) {
        this.id = id;
        this.mail = mail;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;

    }

    public User() {}
    /*
    public void setResetPasswordToken(String token) {
        this.resetPasswordToken = token;
    }

     */

    public void setPassword(String encodedPassword) {
        this.password = password;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getId() {
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
}
