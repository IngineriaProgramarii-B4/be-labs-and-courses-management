package com.example.demo.services;

import com.example.demo.objects.Admin;
import com.example.demo.objects.Student;
import com.example.demo.repositories.AdminRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    public void addAdmins(UUID id, String name, String surname) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        java.util.Date date = new java.util.Date();
        java.util.Date createdAt= new java.util.Date(formatter.format(date));
        java.util.Date updatedAt= new java.util.Date(formatter.format(date));
        Admin admins =new Admin(id,  name, surname, createdAt, updatedAt);
        admin.add(admins);
        adminRepository.save(admins);
    }
    //DELETE
    @Transactional
    public void deleteAdmins(String name, String surname){
        for(Admin admin_iterator: admin){
            if(admin_iterator.getName().equals(name) && admin_iterator.getSurname().equals(surname)){
                admin.remove(admin_iterator);
                adminRepository.delete(admin_iterator);
                break;
            }
        }
    }
    //UPDATE
    @Transactional
    public void updateAdmins(String name, String newName,  String surname, String newSurname){
        updateAdmins();
        for (Admin admin_iterator : admin) {
            if (adminRepository.findById(admin_iterator.getId()).isPresent())
                if (admin_iterator.getName().equals(name) && admin_iterator.getSurname().equals(surname)) {
                    Admin newAdmin=adminRepository.findById(admin_iterator.getId()).get();
                    newAdmin.setName(newName);
                    newAdmin.setSurname(newSurname);
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    Date date = new Date();
                    Date updatedAt= new Date(formatter.format(date));
                    newAdmin.setUpdatedAt(updatedAt);
                    admin.remove(admin_iterator);
                    admin.add(newAdmin);
                    adminRepository.save(newAdmin);
                    break;
                }
        }
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
    public Admin getAdminByNameAndSurname(String name, String surname){
        return adminRepository.findByNameAndSurname(name, surname);
    }

}