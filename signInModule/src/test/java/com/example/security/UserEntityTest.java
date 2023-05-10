package com.example.security;

import com.example.security.model.Role;
import com.example.security.model.UserEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class UserEntityTest {

    @Test
    void testSettersAndGetters() {
        Long userId = 1L;
        String email = "test@example.com";
        String password = "Password123";

        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userId);
        userEntity.setEmail(email);
        userEntity.setPassword(password);

        Assertions.assertEquals(userId, userEntity.getUserId());
        Assertions.assertEquals(email, userEntity.getEmail());
        Assertions.assertEquals(password, userEntity.getPassword());
    }

    @Test
    void testRoleList() {
        Role role1 = new Role();
        role1.setId(1);
        role1.setName("ROLE_ADMIN");

        Role role2 = new Role();
        role2.setId(2);
        role2.setName("ROLE_USER");

        List<Role> roles = new ArrayList<>();
        roles.add(role1);
        roles.add(role2);

        UserEntity userEntity = new UserEntity();
        userEntity.setRoles(roles);

        Assertions.assertEquals(2, userEntity.getRoles().size());
        Assertions.assertEquals(role1, userEntity.getRoles().get(0));
        Assertions.assertEquals(role2, userEntity.getRoles().get(1));
    }
}