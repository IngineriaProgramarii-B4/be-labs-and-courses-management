package com.example.demo.services;

import com.example.demo.objects.Lecture;
import com.example.demo.objects.Student;
import com.example.demo.repositories.SubjectRepository;
import com.example.demo.objects.Subject;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private List<Subject> subjects=new ArrayList<>();
    @Autowired
    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
        updateSubjects();
    }
    public void updateSubjects(){
        subjects=subjectRepository.findAll();
    }
    //CREATE
    @Transactional
    public void addSubject(UUID id, String name, Integer year, Integer semester, Integer credits, Integer idLectures, Integer idSeminars){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        java.util.Date date = new java.util.Date();
        java.util.Date createdAt= new java.util.Date(formatter.format(date));
        java.util.Date updatedAt= new java.util.Date(formatter.format(date));
        Subject subject =new Subject(id, name, year, semester, credits, idLectures, idSeminars, createdAt, updatedAt);
        subjects.add(subject);
        subjectRepository.save(subject);
    }
    //DELETE
    @Transactional
    public void deleteSubject(String name){
        for (Subject subject_iterator : subjects) {
            if (subject_iterator.getName().equals(name)) {
                updateSubjectisDeleted(name);
                break;
            }
        }
    }
    //UPDATE
    @Transactional
    public void updateSubject(String name, String newName, Integer year, Integer semester, Integer credits, Integer idLectures, Integer idSeminars){
       updateSubjects();
       Subject newSubject= new Subject();
        for (Subject subject_iterator : subjects) {
            if (subject_iterator.getName().equals(name)&& !subject_iterator.getisDeleted()) {
                Optional<Subject> newSubjectOp = subjectRepository.findById(subject_iterator.getId());
                if(newSubjectOp.isPresent()){
                    newSubject=newSubjectOp.get();
                }
                    newSubject.setName(newName);
                    if (newSubject.getYear()!=null)
                        newSubject.setYear(year);
                    if (newSubject.getSemester()!=null)
                        newSubject.setSemester(semester);
                    if (newSubject.getCredits()!=null)
                        newSubject.setCredits(credits);
                    if (newSubject.getIdLectures()!=null)
                        newSubject.setIdLectures(idLectures);
                    if (newSubject.getIdSeminars()!=null)
                        newSubject.setIdSeminars(idSeminars);
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    java.util.Date date = new java.util.Date();
                    java.util.Date updatedAt= new java.util.Date(formatter.format(date));
                    newSubject.setUpdatedAt(updatedAt);
                    subjects.remove(subject_iterator);
                    subjects.add(newSubject);
                    subjectRepository.save(newSubject);
                    break;
            }
        }
        System.out.println("Nu s-a gasit niciun obiect de modificat");
    }
    @Transactional
    public void updateSubjectisDeleted(String name){
        updateSubjects();
        Subject newSubject= new Subject();
        for ( Subject subject_iterator : subjects) {
            if (subject_iterator.getName().equals(name) && subjectRepository.findById(subject_iterator.getId()).isPresent()) {
                Optional<Subject> newSubjectOp = subjectRepository.findById(subject_iterator.getId());
                if(newSubjectOp.isPresent()){
                    newSubject=newSubjectOp.get();
                }
                newSubject.setDeleted(true);
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();
                Date updatedAt= new Date(formatter.format(date));
                newSubject.setUpdatedAt(updatedAt);
                subjects.remove(subject_iterator);
                subjects.add(newSubject);
                subjectRepository.save(newSubject);
                break;
            }
        }
    }
    //MAI MULTE FORME DE READ
    @Transactional
    public Subject getSubjectByName(String name){
        for (Subject subject_iterator : subjects) {
            if (subject_iterator.getName().equals(name)) {
                return subject_iterator;
            }
            }
        return null;
    }
    @Transactional
    public List<Subject> getSubjectByYear(Integer year){
        List<Subject> subjectsInYear=new ArrayList<>();
        for (Subject subject_iterator : subjects) {
            if (subject_iterator.getYear().equals(year)) {
                subjectsInYear.add(subject_iterator);
            }
        }
        return subjectsInYear;
    }
    @Transactional
    public List<Subject> getSubjectByYearAndSemester(Integer year,Integer semester){
        List<Subject> subjectsInSemester=new ArrayList<>();
        for (Subject subject_iterator : subjects) {
            if (subject_iterator.getYear().equals(year) && subject_iterator.getSemester().equals(semester)) {
                subjectsInSemester.add(subject_iterator);
            }
        }
        return subjectsInSemester;
    }
}

