package com.example.coursesmodule.repository;

import com.example.coursesmodule.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubjectRepo extends JpaRepository<Subject, Integer> {

    Optional<Subject> findSubjectByTitle(String title);
    List<Subject> findAllByYearAndSemester(int year, int semester);
    void deleteSubjectByTitle(String title);
}
