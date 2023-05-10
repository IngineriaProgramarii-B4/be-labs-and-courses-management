package com.example.demo.services;

import com.example.demo.objects.Grade;
import com.example.demo.objects.Student;
import com.example.demo.repositories.GradeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class GradeService {
    private final GradeRepository gradeRepository;

    private List<Grade> grades=new ArrayList<>();
    @Autowired
    public GradeService(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }
    void updateGreades(){
        grades=gradeRepository.findAll();
    }

    public GradeRepository getGradeRepository() {
        return gradeRepository;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    @Transactional
    public void addGrade(Student user, UUID idLectures, UUID idSeminars, double value){
        UUID idStudent = user.getId();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date createAt = new Date(formatter.format(date));
        Date updateAt = new Date(formatter.format(date));
        Grade grade = new Grade(idStudent, idLectures, idSeminars, value, date,createAt, updateAt);
        grades.add(grade);
        gradeRepository.save(grade);
    }

    @Transactional
    public void updateGrade(Grade grade, double value){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        gradeRepository.findById(grade.getId()).ifPresent(a1->{ a1.setValue(value); a1.setUpdatedAt(new Date(formatter.format(new Date())));
            gradeRepository.save(a1);});
    }

    @Transactional
    public void deleteGrade(Grade grade){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        gradeRepository.findById(grade.getId()).ifPresent(a1->{a1.setDeleted(true); a1.setUpdatedAt(new Date(formatter.format(new Date())));
            gradeRepository.save(a1);});
    }

    @Transactional
    public List<Grade> getGradeByIdStudent(UUID idStudent){
        return gradeRepository.findByIdStudent(idStudent);
    }

    @Transactional
    public List<Grade> getGradesForALecture(UUID idLecture){
        return gradeRepository.findByIdLecture(idLecture);
    }

    @Transactional
    public List<Grade> getGradesForASeminar(UUID idSeminar){
        return gradeRepository.findByIdSeminar(idSeminar);
    }
}