package com.enchere.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Categorie extends HasId{
    @Column(name = "nom")
    private String nom;
}
