package com.example.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.util.Objects;
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
    @Min(value=0)
    @Max(value=2)
    protected int type;

    protected User() {

    }

    protected User(UUID id, String firstname, String lastname, String email, String username, int type) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.username = username;
        this.type = type;
    }

    protected User(String firstname, String lastname, String email, String username, int type) {
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

    @Override
    public boolean equals(Object user) {

        if (this == user)
            return true;

        if (user == null || getClass() != user.getClass())
            return false;

        User user1 = (User) user;
        return type == user1.type && Objects.equals(id, user1.id) && Objects.equals(firstname, user1.firstname) && Objects.equals(lastname, user1.lastname) && Objects.equals(email, user1.email) && Objects.equals(username, user1.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, lastname, email, username, type);
    }
}
