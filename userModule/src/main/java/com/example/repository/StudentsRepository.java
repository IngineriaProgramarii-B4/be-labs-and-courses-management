package com.example.repository;

import com.example.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StudentsRepository extends JpaRepository<Student, String> {

    @Query("select a from Student a where (cast(?1 as uuid)  is null or a.id = ?1) and (?2 is null or a.firstname = ?2) and (?3 is null or a.lastname = ?3) and (?4 is null or a.email = ?4) and (?5 is null or a.username = ?5) and (?6 = 0 or a.year = ?6) and (?7 = 0 or a.semester = ?7) and (?8 is null or a.registrationNumber = ?8)")
    List<Student> findStudentsByParams(UUID id, String firstname, String lastname, String email, String username, Integer year, Integer semester, String registrationNumber);

    @Query("select a from Student a where a.id = ?1")
    Student findStudentById(UUID id);
}