package com.example.demo.services;


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

    @Transactional
    public void addSubject(String name, Integer year, Integer semester, Integer credits, Integer idLectures, Integer idSeminars){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        Date createdAt= new Date(formatter.format(date));
        Date updatedAt= new Date(formatter.format(date));

        Subject subject =new Subject(name, year, semester, credits, idLectures, idSeminars, createdAt, updatedAt);
        subjects.add(subject);
        subjectRepository.save(subject);
    }

    @Transactional
    public void deleteSubject(Subject subject1){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        subjectRepository.findById(subject1.getId()).ifPresent(a1->{ a1.setDeleted(true);a1.setUpdatedAt(new Date(formatter.format(new Date())));
            subjectRepository.save(a1);});
    }

    @Transactional
    public void updateSubject(Subject subject1, String newName, int newCredits){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        subjectRepository.findById(subject1.getId()).ifPresent(a1->{ a1.setName(newName); a1.setCredits(newCredits); a1.setUpdatedAt(new Date(formatter.format(new Date())));
            subjectRepository.save(a1);});
    }

    @Transactional
    public Subject getSubjectByName(String name){
        return subjectRepository.findByName(name);
    }
    @Transactional
    public List<Subject> getSubjectByYear(Integer year){
        return subjectRepository.findByYear(year);
    }
    @Transactional
    public List<Subject> getSubjectByYearAndSemester(Integer year,Integer semester){
        return subjectRepository.findByYearAndSemester(year, semester);
    }
}