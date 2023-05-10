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
    //CREATE
    @Transactional
    public void addLectures(String name) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        java.util.Date date = new java.util.Date();
        java.util.Date createdAt= new java.util.Date(formatter.format(date));
        java.util.Date updatedAt= new java.util.Date(formatter.format(date));
        Lecture lecture =new Lecture(name,createdAt, updatedAt);
        lectures.add(lecture);
        lectureRepository.save(lecture);
    }
    //DELETE
    @Transactional
    public void deleteLectures(String name){
        for(Lecture lectures_iterator: lectures){
            if(lectures_iterator.getName().equals(name)){
                //lectureRepository.delete(lectures_iterator);
                updateLectureIsDeleted(name);
                break;
            }
        }
    }
    //UPDATE
    @Transactional
    public void updateLecture(String name,String newName){
        updateLectures();
        Lecture newLecture = new Lecture();
        for (Lecture lectures_iterator : lectures) {
            if (lectures_iterator.getName().equals(name) && !lectures_iterator.getIsDeleted()) {
                Optional<Lecture> newLectureOp=lectureRepository.findById(lectures_iterator.getId());
                if(newLectureOp.isPresent()){
                    newLecture=newLectureOp.get();
                }
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
        System.out.println("Nu s-a gasit niciun obiect de modificat");
    }

    @Transactional
    public void updateLectureIsDeleted(String name){
        updateLectures();
        Lecture newLecture = new Lecture();
        for (Lecture lectures_iterator : lectures) {
            if (lectures_iterator.getName().equals(name) && !lectures_iterator.getIsDeleted()) {
                Optional<Lecture> newLectureOp=lectureRepository.findById(lectures_iterator.getId());
                if(newLectureOp.isPresent()){
                    newLecture=newLectureOp.get();
                }
                newLecture.setDeleted(true);
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
            if (lecture_iterator.getName().equals(name) && !lecture_iterator.getIsDeleted()) {
                return lecture_iterator;
            }
        }
        return null;
    }

}