package com.example.repository;

import com.example.models.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EvaluationRepo extends JpaRepository<Evaluation, Integer> {
    @Query("SELECT e FROM Subject s JOIN s.evaluationList e WHERE s.title = ?1 AND s.isDeleted = FALSE AND e.isDeleted = FALSE")
    List<Evaluation> findAllBySubjectTitle(String title);

    @Query("SELECT e FROM Subject s JOIN s.evaluationList e WHERE s.title = ?1 AND s.isDeleted = FALSE AND e.component = ?2 AND e.isDeleted = FALSE")
    Optional<Evaluation> findBySubjectTitleAndComponent(String subjectTitle, String component);
}
