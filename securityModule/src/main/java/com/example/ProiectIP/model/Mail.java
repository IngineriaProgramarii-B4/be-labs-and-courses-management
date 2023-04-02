package com.example.ProiectIP.model;

import javax.persistence.*;

//@Entity
//@Table(name = "emails")

public class Mail {
    //repository, adaug automat mail-uri, CRUD pe repo
    //  @Id
   /* @SequenceGenerator(
            name = "email_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "email_sequence",
            strategy = GenerationType.SEQUENCE
    )

    */
    private String mail;

    public String getMail() {
        return mail;
    }

    public Mail(String mail){
        this.mail = mail;
    }

    public Mail() {
    }
}