package com.example.repository;

import com.example.models.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// <-------------------------------- FROM CATALOG ----------------------------------> //

@Repository
public interface GradeRepository extends JpaRepository<Grade,Integer> {

    @Query("SELECT g FROM Grade g WHERE g.id= ?1")
    Optional<Grade> getGradeById(int id);

    @Modifying
    @Query("UPDATE Grade g set g.value= ?1 where g.id= ?2")
    int updateGrade(int value,int id);
}
