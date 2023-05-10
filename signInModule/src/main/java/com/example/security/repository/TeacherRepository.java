package com.example.security.repository;

import com.example.security.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository

public interface TeacherRepository extends JpaRepository<Teacher, UUID> {
    Teacher findByMail(String email);
    Boolean existsByMail(String email);
    Teacher findByRegistrationNumber(Long id);
}
