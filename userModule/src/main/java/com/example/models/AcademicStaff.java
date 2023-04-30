package com.example.models;

import jakarta.persistence.MappedSuperclass;

import java.util.UUID;

@MappedSuperclass
public abstract class AcademicStaff extends User {
    protected String office;

    public AcademicStaff() {

    }
    public AcademicStaff(UUID id, String firstname, String lastname, String email, String username, String office, int type) {
        super(id, firstname, lastname, email, username, type);
        this.office = office;
    }

    public AcademicStaff(String firstname, String lastname, String email, String username, String office, int type) {
        super(firstname, lastname, email, username, type);
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
