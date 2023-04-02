package com.example.ProiectIP.service;
import com.example.ProiectIP.dao.UserRepository;
import com.example.ProiectIP.model.Mail;
import com.example.ProiectIP.model.Password;
import com.example.ProiectIP.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

public class RetrieveUserService {
    @Autowired
    private UserRepository userRepository;

    public boolean findUser(Mail mail, Password password) {
        //User user = userRepository.findBy(mail);
        /*
            cautam in baza de date dupa mail si extragem fiecare informatie dupa o adaugam intr-un obiect User?
         */
        String firstName = userRepository.getFirstName(mail);
        String lastName = userRepository.getLastName(mail);
        String pass = userRepository.getPassword(mail);
        int id = userRepository.getId(mail);
        if(Objects.equals(password.hashAlgorithm(), pass)){
            //send to notificationController that log-in is ok
            return true;
        }
        else
            return false;
        //User user = new User(id, mail.getMail(), pass, firstName, lastName, null);

    }

}
