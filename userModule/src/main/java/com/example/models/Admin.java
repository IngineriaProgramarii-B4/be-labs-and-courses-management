package com.example.models;

//@Entity
//@Tabel
public class Admin extends AcademicStaff {
    private String department;

    public Admin() {

    }

    public Admin(int id,
                 String firstname,
                 String lastname,
                 String email,
                 String username,
                 String password,
                 String office,
                 String department) {

        super(id, firstname, lastname, email, username, password, office);
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
                ", password='" + password + '\'' +
                '}';
    }
}
