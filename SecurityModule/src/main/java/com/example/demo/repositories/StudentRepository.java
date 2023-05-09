package com.example.demo.repositories;

import com.example.demo.objects.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {
    Student findByRegistrationNumber(String registrationNumber);
    Boolean existsByMail(String email);
    List<Student> findByYear(int year);
    List<Student> findByGrupaAndYear(String grupa, int year);
    List<Student> findByFirstName(String firstName);
    List<Student> findByLastName(String lastName);

}
