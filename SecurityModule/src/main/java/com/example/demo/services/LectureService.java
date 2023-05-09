package com.example.demo.services;

import com.example.demo.objects.Lecture;
import com.example.demo.repositories.LectureRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    //CREATE
    @Transactional
    public void addLectures(UUID id, String name) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        java.util.Date date = new java.util.Date();
        java.util.Date createdAt= new java.util.Date(formatter.format(date));
        java.util.Date updatedAt= new java.util.Date(formatter.format(date));
        Lecture lecture =new Lecture(id, name,createdAt, updatedAt);
        lectures.add(lecture);
        lectureRepository.save(lecture);
    }
    //DELETE
    @Transactional
    public void deleteLectures(String name){
        for(Lecture lectures_iterator: lectures){
            if(lectures_iterator.getName().equals(name)){
                lectures.remove(lectures_iterator);
                lectureRepository.delete(lectures_iterator);
                break;
            }
        }
    }
    //UPDATE
    @Transactional
    public void updateLecture(String name,String newName){
        updateLectures();
        for (Lecture lectures_iterator : lectures) {
            if (lectures_iterator.getName().equals(name) && lectureRepository.findById(lectures_iterator.getId()).isPresent()) {
                Lecture newLecture=lectureRepository.findById(lectures_iterator.getId()).get();
                newLecture.setName(newName);
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();
                Date updatedAt= new Date(formatter.format(date));
                newLecture.setUpdatedAt(updatedAt);
                lectures.remove(lectures_iterator);
                lectures.add(newLecture);
                lectureRepository.save(newLecture);
                break;
            }
        }
    }

    @Transactional
    public Lecture getLectureByName(String name){
        for (Lecture lecture_iterator : lectures) {
            if (lecture_iterator.getName().equals(name)) {
                return lecture_iterator;
            }
        }
        return null;
    }

}