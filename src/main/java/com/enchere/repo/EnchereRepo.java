package com.enchere.repo;

import com.enchere.model.Categorie;
import com.enchere.model.Enchere;
import com.enchere.model.Utilisateur;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface EnchereRepo extends JpaRepository<Enchere,Long> {

    @Query(value = "SELECT pourcentage FROM Commission ORDER BY date LIMIT 1;",nativeQuery = true)
    public Double getLastcommission();

    //    Fonction maka ny encheres vita rehetra
    @Query(value = "select * from Enchere where datefin < now() and idutilisateur=?",nativeQuery = true)
    public List<Enchere> getEncheresFinis(Long idutilisateur);

    @Query(value = "select * from Enchere where datefin < now()",nativeQuery = true)
    public List<Enchere> getEncheresFinis();
    List<Enchere> findByUtilisateur(Utilisateur utilisateur);

    @Query(value = "select count(*) from Enchere where datefin > now()", nativeQuery = true)
    public Double getEncheresEnCours();

}
