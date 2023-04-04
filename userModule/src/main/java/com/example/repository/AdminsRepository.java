package com.example.repository;

import com.example.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminsRepository extends JpaRepository<Admin, String> {

    @Query("select a from Admin a where (?1 = 0 or a.id = ?1) and (?2 is null or a.firstname = ?2) and (?3 is null or a.lastname = ?3) and (?4 is null or a.email = ?4) and (?5 is null or a.username = ?5) and (?6 is null or a.office = ?6) and (?7 is null or a.department = ?7)")
    List<Admin> findAdminsByParams(Integer id, String firstname, String lastname, String email, String username, String office, String department);
}