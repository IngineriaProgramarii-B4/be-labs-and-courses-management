package com.example.grades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeService {
    private final GradeRepository repository;

    @Autowired
    public GradeService(GradeRepository repository) {
        this.repository = repository;
    }

    public Grade getGradeById(int id){
        return repository.getGradeById(id).orElse((Grade)null);
    }

    public List<Grade> getGrades(){
        return repository.findAll();
    }
}
