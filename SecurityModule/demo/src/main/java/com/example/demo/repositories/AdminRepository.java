package com.example.demo.repositories;

import com.example.demo.objects.Admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdminRepository extends JpaRepository<Admin, UUID> {
    List<Admin> findByName(String name);
    List<Admin> findBySurname(String surname);
    List<Admin> findByNameAndSurname(String name, String surname);
    Optional<Admin> findById(UUID id);
}