package com.example.securityModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class RetrieveUserService {
    private final UserRepository userRepository;
    @Autowired
    public RetrieveUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUser(Mail mail, Password password) {
        //User user = userRepository.findBy(mail);
        /*
            cautam in baza de date dupa mail si extragem fiecare informatie dupa o adaugam intr-un obiect User?
         */
        //String firstName = userRepository.getFirstName(mail);
        //String lastName = userRepository.getLastName(mail);
        Password pass = userRepository.findByMail(mail);
        int id = userRepository.getIdByMail(mail);
        if(Objects.equals(password.hash(), pass.hash())){
            //send to notificationController that log-in is ok
            return userRepository.findById(id);
            //return true;
        }
        else {
            System.out.println("Nu s-a gasit user cu mail si parola matching!");
            return null;
        }

        //User user = new User(id, mail.getMail(), pass, firstName, lastName, null);

    }

}
