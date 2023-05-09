package com.example.demo.services;

import com.example.demo.objects.Seminar;
import com.example.demo.repositories.SeminarRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class SeminarService {
    private final SeminarRepository seminarRepository;
    private List<Seminar> seminar=new ArrayList<>();
    @Autowired
    public SeminarService(SeminarRepository seminarRepository) {
        this.seminarRepository = seminarRepository;
        updateSeminars();
    }
    public void updateSeminars(){

        seminar=seminarRepository.findAll();
    }
    public SeminarRepository getSeminarsRepository() {

        return seminarRepository;
    }

    //CREATE
    @Transactional
    public void addSeminars(UUID id, String name) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        java.util.Date date = new java.util.Date();
        java.util.Date createdAt= new java.util.Date(formatter.format(date));
        java.util.Date updatedAt= new java.util.Date(formatter.format(date));
        Seminar seminars =new Seminar(id, name,createdAt, updatedAt);
        seminar.add(seminars);
        seminarRepository.save(seminars);
    }
    //DELETE
    @Transactional
    public void deleteSeminars(String name){
        for(Seminar seminar_iterator: seminar){
            if(seminar_iterator.getName().equals(name)){
                seminar.remove(seminar_iterator);
                seminarRepository.delete(seminar_iterator);
                break;
            }
        }
    }
    @Transactional
    public void updateSeminars(String name,String newName){
        updateSeminars();
        for (Seminar seminar_iterator : seminar) {
            if (seminar_iterator.getName().equals(name) && seminarRepository.findById(seminar_iterator.getId()).isPresent()) {
                Seminar newSeminar=seminarRepository.findById(seminar_iterator.getId()).get();
                newSeminar.setName(newName);
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();
                Date updatedAt= new Date(formatter.format(date));
                newSeminar.setUpdatedAt(updatedAt);
                seminar.remove(seminar_iterator);
                seminar.add(newSeminar);
                seminarRepository.save(newSeminar);
                break;
            }
        }
    }
    //READ
    @Transactional
    public Seminar getSeminarByName(String name){
        for (Seminar seminar_iterator :seminar ) {
            if (seminar_iterator.getName().equals(name)) {
                return seminar_iterator;
            }
        }
        return null;
    }

}