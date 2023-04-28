package com.example.coursesmodule.repository;

import com.example.coursesmodule.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SubjectRepo extends JpaRepository<Subject, Integer> {

    Optional<Subject> findSubjectByTitle(String title);
    @Query("SELECT s FROM Subject s WHERE s.year = ?1 AND s.semester = ?2")
    List<Subject> findAllByYearAndSemester(int year, int semester);
    void deleteSubjectByTitle(String title);
}
