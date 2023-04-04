package com.example.security.repository;

import com.example.security.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    UserEntity findByEmail(String email);
    Boolean existsByEmail(String email);
    UserEntity findByUserId(Long id);
}
