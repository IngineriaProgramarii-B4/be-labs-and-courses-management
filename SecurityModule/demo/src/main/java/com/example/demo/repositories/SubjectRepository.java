package com.example.demo.repositories;
import com.example.demo.objects.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, UUID> {
    Subject findByName(String name);
    List<Subject> findByYear(Integer year);
    List<Subject> findByYearAndSemester(Integer year, Integer semester);

}
