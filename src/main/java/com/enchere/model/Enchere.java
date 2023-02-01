package com.enchere.model;

import jakarta.persistence.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Entity
@Getter
@Setter
public class Enchere extends HasId{

    @Column(name="nom")
    private String nom;

    @Column(name="description")
    private String description;


    @ManyToOne
    @JoinColumn(name = "idcategorie")
    private Categorie categorie;

    @Column(name="prixminimal")
    private double prixminimal;

    @Column(name="duree")
    private Double duree;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP",insertable = false)
    private LocalDateTime datedebut;


    @ManyToOne
    @JoinColumn(name = "idutilisateur")
    private Utilisateur utilisateur;

    @Column(name="commission")
    private Double commission;

    @Column(name="datefin")
    private LocalDateTime datefin;

    @OneToMany(mappedBy = "idenchere")
    private List<Photo> photos;

    @Transient
    private Integer status;

    public Integer getStatus() {
        if (datefin.isAfter(LocalDateTime.now())){
             status = 0;
        }
        else {
            status = 1;
        }
        return status;
    }

    public Categorie getCategorie() {
        return (Categorie) Hibernate.unproxy(categorie);
    }

    public Utilisateur getUtilisateur() {
        return (Utilisateur) Hibernate.unproxy(utilisateur);
    }
}
