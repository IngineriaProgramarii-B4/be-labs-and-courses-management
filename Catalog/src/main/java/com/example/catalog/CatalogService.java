package com.example.catalog;

import com.example.user_impl.student.Student;
import com.example.user_impl.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Service
public class CatalogService {
    private final StudentService studentService;

    private final Catalog catalog = new Catalog();

    @Autowired
    public CatalogService(StudentService studentService) {
        this.studentService = studentService;
        initCatalogDB();
    }

    public void initCatalogDB(){
        catalog.setEntries(studentService.getStudentDataBase());
    }
    @CrossOrigin(origins = "*")
    public List<Student> getCatalog(){
        return studentService.getStudentDataBase();
    }
}
