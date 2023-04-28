package com.example.security.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class UserEntity {

    @Id
    private Long userId;

    @Email
    private String email;
    @Pattern(regexp = "^(?=.*[!@#$%^&*()])(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$", message = "The password must contain at least 8 characters, at least one digit, at least one special symbol and at least one capital letter")

    private String password;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "userId"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles = new ArrayList<>();
}
