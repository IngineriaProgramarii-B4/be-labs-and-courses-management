package com.example.demo.objects;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name="roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
}
//asta o completeaza adminul
//1=ADMIN, 2=TEACHER, 3=STUDENT astea vor fi in tabela roles

//TODO FACUT RESTUL SERVICIILOR pentru role
//TODO am adaugat registration number la teacher. trebuie verificat sa fie ok in constructori si settere, gettere, etc.
//TODO

