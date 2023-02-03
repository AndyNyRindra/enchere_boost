package com.enchere.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Entity
@Getter
@Setter
public class Proposition extends HasId{
    @ManyToOne
    @JoinColumn(name = "idutilisateur")
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "idenchere")
    private Enchere enchere;

    @Column(name = "prix")
    private Double prix;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP",insertable = false)
    private LocalDateTime dateproposition   ;

    @Column(name = "plushaut")
    private boolean plushaut=true;

}
