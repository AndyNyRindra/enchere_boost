package com.enchere.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Utilisateur extends HasId{

    @Column(name="nom")
    private String nom;

    @Column(name="prenom")
    private String prenom;

    @Column(name="contact")
    private String contact;
    @Column(name="email")
    private String email;
    @Column(name="mdp")
    private String mdp;

}
