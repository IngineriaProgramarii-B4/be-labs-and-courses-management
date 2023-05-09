package com.example.demo.services;

import com.example.demo.objects.Lecture;
import com.example.demo.objects.Seminar;
import com.example.demo.objects.Student;
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

    //CREATE
    @Transactional
    public void addSeminars( String name) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        java.util.Date date = new java.util.Date();
        java.util.Date createdAt= new java.util.Date(formatter.format(date));
        java.util.Date updatedAt= new java.util.Date(formatter.format(date));
        Seminar seminars =new Seminar( name,createdAt, updatedAt);
        seminar.add(seminars);
        seminarRepository.save(seminars);
    }
    //DELETE
    @Transactional
    public void deleteSeminars(String name){
        for(Seminar seminar_iterator: seminar){
            if(seminar_iterator.getName().equals(name)){
                updateSeminarsIsDeleted(name);
                break;
            }
        }
    }
    @Transactional
    public void updateSeminars(String name,String newName){
        updateSeminars();
        Seminar newSeminar=new Seminar();
        for (Seminar seminar_iterator : seminar) {
            if (seminar_iterator.getName().equals(name) && !seminar_iterator.getisDeleted()) {
                Optional<Seminar> newSeminarOp = seminarRepository.findById(seminar_iterator.getId());
                if(newSeminarOp.isPresent()){
                    newSeminar=newSeminarOp.get();
                }
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
        System.out.println("Nu s-a gasit niciun obiect de modificat");
    }
    @Transactional
    public void updateSeminarsIsDeleted(String name){
        updateSeminars();
        Seminar newSeminar=new Seminar();
        for (Seminar seminar_iterator : seminar) {
            if (seminar_iterator.getName().equals(name) && seminarRepository.findById(seminar_iterator.getId()).isPresent()) {
                Optional<Seminar> newSeminarOp = seminarRepository.findById(seminar_iterator.getId());
                if(newSeminarOp.isPresent()){
                    newSeminar=newSeminarOp.get();
                }
                newSeminar.setDeleted(true);
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