package com.enchere.repo;

import com.enchere.model.RechargementCompte;
import com.enchere.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RechargementCompteRepo extends JpaRepository<RechargementCompte, Long> {
// Fonction maka ny montant no-recharger-na utilisateur ray
    @Query(value = "select coalesce(sum(montant),0) from RechargementCompte where utilisateur=:utilisateur and status=1")
    public double getSommeMontantRecharge(@Param("utilisateur") Utilisateur utilisateur);

    public List<RechargementCompte> findByStatus(Integer status);
}
