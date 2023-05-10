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
    public void deleteGrade(Student user){
        UUID idStudent = user.getId();
        System.out.println(idStudent);

        //List<Grade> removeGrade = new ArrayList<>(); //obiectele ce vor fi sterse din lista grades

        for (Grade grade : grades) {
            if (grade.getIdStudent().equals(idStudent)) {
                //gradeRepository.delete(grade);
                updateGradeIsDeleted(user);
                break;
            }
        }


    }

    @Transactional
    public void updateGrade(Student student, UUID idLectures, UUID idSeminars, double value){
        UUID idStudent=student.getId();
        //UUID idLectures=lectures.getId();
        //UUID idSeminars=seminars.getId();
        updateGreades();
        Grade newGrade = new Grade();
        for (Grade grade_iterator : grades) {
            if (grade_iterator.getIdStudent().equals(idStudent) && !grade_iterator.getIsDeleted()) {
                System.out.println(grade_iterator.getId() + "GRADES");
                Optional<Grade> newGradeOp = gradeRepository.findById(grade_iterator.getId());
                if(newGradeOp.isPresent()){
                    newGrade=newGradeOp.get();
                }
                newGrade.setGrade(value);
                newGrade.setIdStudent(idStudent);
                newGrade.setIdSeminars(idSeminars);
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
        System.out.println("Nu s-a gasit niciun obiect de modificat");
    }

    @Transactional
    public void updateGradeIsDeleted(Student user){
        UUID idStudent=user.getId();
        //UUID idLectures=lectures.getId();
        //UUID idSeminars=seminars.getId();
        updateGreades();
        Grade newGrade = new Grade();
        for (Grade grade_iterator : grades) {
            if (grade_iterator.getIdStudent().equals(idStudent) && !grade_iterator.getIsDeleted()) {
                System.out.println(grade_iterator.getId() + "GRADES");
                Optional<Grade> newGradeOp = gradeRepository.findById(grade_iterator.getId());
                if(newGradeOp.isPresent()){
                    newGrade=newGradeOp.get();
                }
                newGrade.setDeleted(true);
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