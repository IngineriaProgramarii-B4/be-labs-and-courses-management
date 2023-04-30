package com.example.repository;

import com.example.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AdminsRepository extends JpaRepository<Admin, String> {
    @Query("select a from Admin a where (cast(?1 as uuid) is null or a.id = ?1) and (?2 is null or a.firstname = ?2) and (?3 is null or a.lastname = ?3) and (?4 is null or a.email = ?4) and (?5 is null or a.username = ?5) and (?6 is null or a.office = ?6) and (?7 is null or a.department = ?7)")
    List<Admin> findAdminsByParams(UUID id, String firstname, String lastname, String email, String username, String office, String department);

    @Modifying
    @Query("update Admin a set a.firstname = COALESCE(?2, a.firstname), " +
            "a.lastname = COALESCE(?3, a.lastname), " +
            "a.email = COALESCE(?4, a.email), " +
            "a.username = COALESCE(?5, a.username), " +
            "a.office = COALESCE(?6, a.office), " +
            "a.department = COALESCE(?7, a.department) " +
            "where a.id = ?1")
    void updateAdmin(UUID uuid, String firstname, String lastname, String email, String username, String office, String departament);

}