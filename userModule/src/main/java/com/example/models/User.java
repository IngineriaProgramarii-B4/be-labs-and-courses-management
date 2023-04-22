package com.example.models;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID id;
    protected String firstname;
    protected String lastname;
    protected String email;
    protected String username;

    /*
    * 0 - admin
    * 1 - teacher
    * 2 - student
    * */
    protected int type;

    public User() {

    }

    public User(String firstname, String lastname, String email, String username, int type) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.username = username;
        this.type = type;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

}