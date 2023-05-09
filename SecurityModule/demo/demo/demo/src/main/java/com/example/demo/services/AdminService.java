package com.example.demo.services;

import com.example.demo.objects.Admin;
import com.example.demo.objects.Lecture;
import com.example.demo.objects.Student;
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
               updateAdminIsDeleted(name,surname);
                break;
            }
        }
    }
    //UPDATE
    @Transactional
    public void updateAdmins(String name, String newName,  String surname, String newSurname){
        updateAdmins();
        Admin newAdmin= new Admin();
        for (Admin admin_iterator : admin) {
            if (admin_iterator.getName().equals(name) && admin_iterator.getSurname().equals(surname)&& !admin_iterator.getisDeleted()) {
                Optional<Admin> newAdminOp = adminRepository.findById(admin_iterator.getId());
                if(newAdminOp.isPresent()){
                    newAdmin=newAdminOp.get();
                }
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
        System.out.println("Nu s-a gasit niciun obiect de modificat");
    }
    @Transactional
    public void updateAdminIsDeleted(String name, String surname){
        updateAdmins();
        Admin newAdmin= new Admin();
        for (Admin admin_iterator : admin) {
            if (admin_iterator.getName().equals(name) && admin_iterator.getSurname().equals(surname) && adminRepository.findById(admin_iterator.getId()).isPresent()) {
                Optional<Admin> newAdminOp = adminRepository.findById(admin_iterator.getId());
                if(newAdminOp.isPresent()){
                    newAdmin=newAdminOp.get();
                }
                newAdmin.setDeleted(true);
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
    public Admin getAdminByName(String name){
        for(Admin admin_iterator : admin){
            if(admin_iterator.getName().equals(name)){
                return admin_iterator;
            }
        }
        return null;
    }
   @Transactional
    public Admin getAdminBySurname(String surname){
        for(Admin admin_iterator : admin){
            if(admin_iterator.getSurname().equals(surname)){
                return admin_iterator;
            }
        }
        return null;
    }

    @Transactional
    public Admin getAdminByNameAndSurname(String name, String surname){
        for(Admin admin_iterator : admin){
            if(admin_iterator.getName().equals(name) && admin_iterator.getSurname().equals(surname)){
                return admin_iterator;
            }
        }
        return null;
    }

}

