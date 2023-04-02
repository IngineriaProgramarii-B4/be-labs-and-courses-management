package com.example.ProiectIP.service;
import com.example.ProiectIP.dao.UserRepository;
import com.example.ProiectIP.model.Mail;
import com.example.ProiectIP.model.Password;
import com.example.ProiectIP.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserService {
    @Autowired
    private UserRepository userRepository;
    public void createUser(Mail mail, Password password, int id, String firstName, String lastName) {
        String hashedPassword = password.hashAlgorithm();
        String email = mail.getMail();
        User user = new User(id, email, hashedPassword, firstName, lastName, null);
        //TODO: verificare tip user
        userRepository.save(user);
    }

    //TODO: metoda pt reset mail -> trimitere notificare

    public void resetPassword(String token, Mail mail){
        User user = (User) userRepository.findByEmail(mail);
        if(user!=null)
        {
            user.setResetPasswordToken(token);
            userRepository.save(user);
        }
    }
    public User getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }

    public void updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        user.setResetPasswordToken(null);
        userRepository.save(user);
    }

    public void changeMail(User user, Mail mail, String newMail){
        if(userRepository.findByEmail(mail)!=null){
            user.setMail(newMail);
            userRepository.save(user);
        }
    }
}

//TODO: clasele care logheaza login/signup, notificarile, reset password,