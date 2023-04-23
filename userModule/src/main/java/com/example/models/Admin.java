package com.example.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "admins")
public class Admin extends AcademicStaff {
    private String department;

    public Admin() {

    }

    public Admin(String firstname,
                 String lastname,
                 String email,
                 String username,
                 String office,
                 String department) {

        super(firstname, lastname, email, username, office);
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "department='" + department + '\'' +
                ", office='" + office + '\'' +
                ", id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}