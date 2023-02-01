package com.enchere.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Photo extends HasId{
    Long idenchere;
    String bytes;

}
