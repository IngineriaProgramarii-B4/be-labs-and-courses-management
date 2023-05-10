package com.example.demo.services;

import com.example.demo.objects.Seminar;
import com.example.demo.repositories.SeminarRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

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


    @Transactional
    public void addSeminars( String name) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        Date createdAt= new Date(formatter.format(date));
        Date updatedAt= new Date(formatter.format(date));
        Seminar seminars =new Seminar( name,createdAt, updatedAt);
        seminar.add(seminars);
        seminarRepository.save(seminars);
    }

    @Transactional
    public void updateSeminar(Seminar seminar, String newName){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        seminarRepository.findById(seminar.getId()).ifPresent(a1->{ a1.setName(newName); a1.setUpdatedAt(new Date(formatter.format(new Date())));
            seminarRepository.save(a1);});
    }
    @Transactional
    public void deleteSeminar(Seminar seminar){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        seminarRepository.findById(seminar.getId()).ifPresent(a1->{
            a1.setDeleted(true);
            a1.setUpdatedAt(new Date(formatter.format(new Date())));
            seminarRepository.save(a1);});
    }

    @Transactional
    public List<Seminar> getSeminarByName(String name){
        return seminarRepository.findByName(name);
    }

}