package com.enchere.service;

import com.enchere.model.Enchere;
import com.enchere.recherche.ConstructionRequete;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RechercheEnchere {
    @PersistenceContext
    EntityManager entity;

    public List<Enchere> rechercheMotCle(String key){
        String sql = "select * from Enchere where description ilike '%"+key+"%'";
        System.out.println(sql);
        Query query = entity.createNativeQuery(sql, Enchere.class);
        List<Enchere> list = query.getResultList();
        return list;
    }

    public List<Enchere> rechercheAvancee(ConstructionRequete c){
        List<Enchere> valiny = null;
        try {
            Query query = entity.createNativeQuery(c.creerRequete(), Enchere.class);
            valiny = query.getResultList();
        }catch (Exception e){
            e.printStackTrace();
        }
        return valiny;
    }
}
