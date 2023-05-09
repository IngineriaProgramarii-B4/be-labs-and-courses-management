package com.example.demo.repositories;

import com.example.demo.objects.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.UUID;

@Repository
public interface AdminRepository extends JpaRepository<Admin, UUID> {
}