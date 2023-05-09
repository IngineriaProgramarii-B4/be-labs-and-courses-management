package com.example.user_impl.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer> {

    @Query("SELECT s FROM Student s WHERE s.id= ?1")
    Optional<Student> getStudentById(Integer id);


    @Query("SELECT s FROM Student s")
    List<Student> getAllStudents();

}
