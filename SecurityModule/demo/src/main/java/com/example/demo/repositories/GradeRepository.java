package com.example.demo.repositories;

import com.example.demo.objects.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.UUID;

@Repository
public interface GradeRepository extends JpaRepository<Grade, UUID> {
    List<Grade> findByIdStudent(UUID idStudent);
    List<Grade> findByIdLecture(UUID idLecture);
    List<Grade> findByIdSeminar(UUID idSeminar);
}