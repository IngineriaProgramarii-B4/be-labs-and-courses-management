package com.example.demo.services;

import com.example.demo.objects.Admin;
import com.example.demo.repositories.AdminRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private List<Admin> admin =new ArrayList<>();
    @Autowired
    public AdminService(AdminRepository adminsRepository) {
        this.adminRepository = adminsRepository;
        updateAdmins();
    }
    public  void updateAdmins(){
        admin = adminRepository.findAll();
    }
    public AdminRepository getAdminsRepository() {

        return adminRepository;
    }

    //CREATE
    @Transactional
    public void addAdmins(String name, String surname) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        Date createdAt= new Date(formatter.format(date));
        Date updatedAt= new Date(formatter.format(date));
        Admin admins =new Admin(name, surname, createdAt, updatedAt);
        admin.add(admins);
        adminRepository.save(admins);
    }

    @Transactional
    public void updateAdmins(Admin admin1, String newName, String newSurname){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        adminRepository.findById(admin1.getId()).ifPresent(a1->{ a1.setName(newName); a1.setSurname(newSurname); a1.setUpdatedAt(new Date(formatter.format(new Date())));
            adminRepository.save(a1);});
        adminRepository.delete(admin1);
    }
    @Transactional
    public void deleteAdmins(Admin admin1){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        adminRepository.findById(admin1.getId()).ifPresent(a1->{ a1.setDeleted(true);a1.setUpdatedAt(new Date(formatter.format(new Date())));
            adminRepository.save(a1);});
        adminRepository.delete(admin1);
    }

    @Transactional
    public List<Admin> getAdminByName(String name){
        return adminRepository.findByName(name);
    }
    @Transactional
    public List<Admin> getAdminBySurname(String surname){
        return adminRepository.findBySurname(surname);
    }

    @Transactional
    public List<Admin> getAdminByNameAndSurname(String name, String surname){
        return adminRepository.findByNameAndSurname(name, surname);

    }

}