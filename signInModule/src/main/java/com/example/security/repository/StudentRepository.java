package com.example.security.repository;

import com.example.security.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository

public interface StudentRepository extends JpaRepository<Student, UUID> {
    Student findByMail(String email);
    Boolean existsByMail(String email);
    Student findByRegistrationNumber(Long id);
}
