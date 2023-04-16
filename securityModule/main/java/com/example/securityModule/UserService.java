package com.example.securityModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    //@Autowired
    //@Bean
    public void createUser(User user) {
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String mail = user.getMail();
        Password password = new Password("salut123");
        String hashedPassword = password.hash();
        //Password hashedPassword = new Password(hashedString);
        int id = user.getId();
        User user1 = new User(id, firstName, lastName, mail, hashedPassword);
        //TODO: verificare tip user
        userRepository.save(user1);
    }
    public void deleteUser(User user) {

    }
}
