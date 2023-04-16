package com.example.securityModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class GradeService {
    private final GradeRepository gradeRepository ;
    @Autowired
    public GradeService(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }
    public void addGrade(User user, int id, int value, int idLectures, int idSeminars) {
        int idStudent = user.getId();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date updatedAt = new Date(formatter.format(date));
        Grade grade = new Grade(id, idLectures, idSeminars, idStudent, value, updatedAt, updatedAt);
        gradeRepository.save(grade);
    }
}
