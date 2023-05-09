package com.example.demo.repositories;

import com.example.demo.objects.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, UUID> {
    Teacher findByMail(String mail);

    List<Teacher> findByDegree(String degree);

    List<Teacher> findByLastName(String lastName);
}







