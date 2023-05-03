package com.example.user_impl.teacher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher,Integer> {

    @Query("SELECT t FROM Teacher t WHERE t.id= ?1")
    Optional<Teacher> getTeacherById(Integer id);

    @Query("SELECT t FROM Teacher t")
    List<Teacher> getAllTeachers();
}
