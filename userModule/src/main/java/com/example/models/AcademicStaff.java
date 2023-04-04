package com.example.models;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AcademicStaff extends AppUser {
    protected String office;

    public AcademicStaff() {

    }

    public AcademicStaff(int id, String firstname, String lastname, String email, String username, String office) {
        super(id, firstname, lastname, email, username);
        this.office = office;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public void manageCatalog() {

    }

    public void postAnnouncement() {

    }

    public void addResource() {

    }

    public void generateReports() {

    }
}