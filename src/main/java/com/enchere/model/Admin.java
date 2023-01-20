package com.enchere.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
public class Admin extends HasIdMongo{
    private String email;
    private String mdp;

}
