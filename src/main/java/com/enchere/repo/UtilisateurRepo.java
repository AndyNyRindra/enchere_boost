package com.enchere.repo;

import com.enchere.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepo extends JpaRepository<Utilisateur,Long> {
    public Utilisateur findByEmailAndMdp(String email, String mdp);
}
