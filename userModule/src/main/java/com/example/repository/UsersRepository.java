package com.example.repository;

import com.example.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<AppUser, String> {

    @Query("select a from AppUser a where (?1 = 0 or a.id = ?1) and (?2 is null or a.firstname = ?2) and (?3 is null or a.lastname = ?3) and (?4 is null or a.email = ?4) and (?5 is null or a.username = ?5)")
    List<AppUser> findUserByParams(Integer id, String firstname, String lastname, String email, String username);

}