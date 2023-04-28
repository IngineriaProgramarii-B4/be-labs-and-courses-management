package com.example.security.security;

import com.example.security.model.Role;
import com.example.security.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.security.repository.UserRepository;

import java.util.List;

@Service

public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email);
        if(user == null)
            throw new UsernameNotFoundException("Username not found");
        return new User(user.getEmail(),user.getPassword(),mapRolesToAuthorities(user.getRoles()));
    }
    private List<SimpleGrantedAuthority> mapRolesToAuthorities(List<Role> roles)
    {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList();
    }
}
