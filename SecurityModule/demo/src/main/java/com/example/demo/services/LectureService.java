package com.example.demo.services;

import com.example.demo.objects.Lecture;
import com.example.demo.repositories.LectureRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class LectureService {
    private final LectureRepository lectureRepository;
    private List<Lecture> lectures=new ArrayList<>();
    @Autowired
    public LectureService(LectureRepository lectureRepository) {
        this.lectureRepository = lectureRepository;
        updateLectures();
    }
    public void updateLectures(){
        lectures=lectureRepository.findAll();
    }
    public LectureRepository getLecturesRepository() {
        return lectureRepository;
    }

    @Transactional
    public void addLectures(String name) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        Date createdAt= new Date(formatter.format(date));
        Date updatedAt= new Date(formatter.format(date));
        Lecture lecture =new Lecture(name,createdAt, updatedAt);
        lectures.add(lecture);
        lectureRepository.save(lecture);
    }

    @Transactional
    public void updateLecture(Lecture lecture,String name){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        lectureRepository.findById(lecture.getId()).ifPresent(a1->{ a1.setName(name);a1.setUpdatedAt(new Date(formatter.format(new Date())));
            lectureRepository.save(a1);});
    }

    @Transactional
    public void deleteLecture(Lecture lecture){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        lectureRepository.findById(lecture.getId()).ifPresent(a1->{ a1.setDeleted(true);a1.setUpdatedAt(new Date(formatter.format(new Date())));
            lectureRepository.save(a1);});
    }

    @Transactional
    public List<Lecture> getLectureByName(String name){
        return lectureRepository.findByName(name);
    }

}