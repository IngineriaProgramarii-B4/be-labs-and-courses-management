package com.example.coursesmodule.repository;

import com.example.coursesmodule.model.Component;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ComponentRepository extends JpaRepository<Component, Integer> {
   @Query("SELECT c FROM Subject s JOIN s.componentList c WHERE s.title = ?1")
   List<Component> findAllBySubjectTitle(String title);

   @Query("SELECT c FROM Subject s JOIN s.componentList c WHERE s.title = ?1 AND c.type = ?2")
   Optional<Component> findBySubjectTitleAndType(String subjectTitle, String type);
}
