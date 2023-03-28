package com.example.models;

import jakarta.persistence.*;

import java.io.File;
import java.util.UUID;

//@MappedSuperclass
@Table
@Entity
public abstract class AppUser {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_squence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    protected int id; //could be of UUID type
    protected String firstname;
    protected String lastname;
    protected String email;
    protected String username;
    protected String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public void login(String username, String password) {
//        //todo
//    }
//
//    public void logout() { //no args needed
//        //todo
//    }
//
//    public void getCatalog() {
//
//    }
//
//    public void seeAnnouncements() {
//
//    }
//
//    public void getNetwork() {
//
//    }
//
//    public void setReminders() {
//
//    }
//
//    void seeResource(File resource) {
//
//    }

    public AppUser() {

    } //default ctor

    public AppUser(int id, String firstname, String lastname, String email, String username, String password) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.username = username;
        this.password = password;
    }

}
