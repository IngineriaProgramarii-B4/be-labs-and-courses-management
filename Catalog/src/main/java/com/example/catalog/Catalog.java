package com.example.catalog;

import com.example.user_impl.student.Student;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Table
public class Catalog {

    @OneToMany
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
