package com.example.securityModule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    //List<User> findByEmail(@Param(value="mail") Mail mail);
    //List<User> findByNameAndEmail(String name, Mail mail);
    //public User findByResetPasswordToken(String token);
    public User findById(@Param(value = "id") int id);
    //public String getFirstName(@Param(value = "email") Mail mail);
    //public String getLastName(@Param(value = "email") Mail mail);
    public Password findByMail(@Param(value = "mail") Mail mail);
    public Integer getIdByMail(@Param(value = "mail") Mail mail); //TODO schimbat Integer -> UUID
    //Optional<User> findById(int id);


}
