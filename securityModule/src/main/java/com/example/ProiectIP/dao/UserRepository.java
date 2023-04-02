package com.example.ProiectIP.dao;

import com.example.ProiectIP.model.Mail;
import com.example.ProiectIP.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    //TODO schimbat Integer cu UUID
    public List<Mail> findByNameContainingIgnoreCase(@Param(value = "name") String name);
    List<User> findByEmail(@Param(value="mail") Mail mail);
    List<User> findByNameAndEmail(String name, Mail mail);
    public User findByResetPasswordToken(String token);
    public String getFirstName(@Param(value = "email") Mail mail);
    public String getLastName(@Param(value = "email") Mail mail);
    public String getPassword(@Param(value = "email") Mail mail);
    public Integer getId(@Param(value = "email") Mail mail); //TODO schimbat Integer -> UUID

    //public List<Mail> getRandomMails(@Param(value = "number") Integer number);

}
