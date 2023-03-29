package com.example.catalog;

import com.example.userImpl.student.Student;

import java.util.ArrayList;
import java.util.List;

public class Catalog {
    private List<Student> entries = new ArrayList<>();
    public Catalog(){}
    public Catalog(List<Student> entries) {
        this.entries = entries;
    }
    public void addEntry(Student student){
        entries.add(student);
    }

    public List<Student> getEntries() {
        return entries;
    }

    public void setEntries(List<Student> entries) {
        this.entries = entries;
    }
}