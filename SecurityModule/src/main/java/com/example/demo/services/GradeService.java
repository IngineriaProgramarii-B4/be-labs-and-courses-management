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
    public void addGrade(Student student, UUID idLectures, UUID idSeminars, double value){
        UUID idStudent = student.getId();
        //UUID idLectures = lectures.getId();
        //UUID idSeminars = seminars.getId();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date createAt = new Date(formatter.format(date));
        Date updateAt = new Date(formatter.format(date));
        Grade grade = new Grade(idStudent, idLectures, idSeminars, value, date,createAt, updateAt);
        grades.add(grade);
        gradeRepository.save(grade);
    }

    @Transactional
    public void deleteGrade(Student student){
        UUID idStudent = student.getId();
        System.out.println(idStudent);

        List<Grade> removeGrade = new ArrayList<>(); //obiectele ce vor fi sterse din lista grades

        for (Grade grade : grades) {
            if (grade.getIdStudent().equals(idStudent)) {
                removeGrade.add(grade);
                gradeRepository.delete(grade);
            }
        }

        for(Grade rg : removeGrade) {
            grades.remove(rg);
        }
    }

    @Transactional
    public void updateGrade(Student student, UUID idLectures, UUID idSeminars, double value){
        UUID idStudent=student.getId();
        //UUID idLectures=lectures.getId();
        //UUID idSeminars=seminars.getId();
        updateGreades();
        for (Grade grade_iterator : grades) {
            if (grade_iterator.getIdStudent().equals(idStudent)) {
                System.out.println(grade_iterator.getId() + "GRADES");
                Grade newGrade=gradeRepository.findById(grade_iterator.getId()).get();
                newGrade.setGrade(value);
                if (newGrade.getIdStudent()!=null)
                    newGrade.setIdStudent(idStudent);
                if (newGrade.getIdSeminars()!=null)
                    newGrade.setIdSeminars(idSeminars);
                if (newGrade.getIdLectures()!=null)
                    newGrade.setIdLectures(idLectures);

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                java.util.Date date = new java.util.Date();
                java.util.Date updatedAt= new java.util.Date(formatter.format(date));
                newGrade.setUpdatedAt(updatedAt);
                grades.remove(grade_iterator);
                grades.add(newGrade);
                gradeRepository.save(newGrade);
                break;
            }
        }
    }

    @Transactional
    public Grade getGradeByIdStudent(UUID idStudent){
        for(Grade gr_it : grades){
            if(gr_it.getIdStudent().equals(idStudent)){
                return gr_it;
            }
        }
        return null;
    }

    @Transactional
    public List<Grade> getGradesForALecture(UUID idLecture){
        List<Grade> gradesForALecture=new ArrayList<>();
        for(Grade gr_it : grades){
            if(gr_it.getIdLectures().equals(idLecture)){
                gradesForALecture.add(gr_it);
            }
        }
        return gradesForALecture;
    }

    @Transactional
    public List<Grade> getGradesForASeminar(UUID idSeminar){
        List<Grade> gradesForASeminar=new ArrayList<>();
        for(Grade gr_it : grades){
            if(gr_it.getIdSeminars().equals(idSeminar)){
                gradesForASeminar.add(gr_it);
            }
        }
        return gradesForASeminar;
    }
}