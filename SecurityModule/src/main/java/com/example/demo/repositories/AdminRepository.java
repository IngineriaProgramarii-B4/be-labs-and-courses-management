package com.example.demo.repositories;

import com.example.demo.objects.Admin;
import com.example.demo.objects.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.UUID;

@Repository
public interface AdminRepository extends JpaRepository<Admin, UUID> {
    List<Admin> findByName(String name);
    List<Admin> findBySurname(String surname);
    Admin findByNameAndSurname(String name, String surname);
}