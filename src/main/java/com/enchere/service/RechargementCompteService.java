package com.enchere.service;

import com.enchere.exception.CustomException;
import com.enchere.model.RechargementCompte;
import com.enchere.model.Utilisateur;
import com.enchere.repo.RechargementCompteRepo;
import com.enchere.service.common.CrudService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RechargementCompteService extends CrudService<RechargementCompte, RechargementCompteRepo> {
    public RechargementCompteService(RechargementCompteRepo repo) {
        super(repo);
    }

//    Fonction valider rechargement utilisateur
    public void validerRechargement(Long id) throws CustomException {
        Optional<RechargementCompte> r = repo.findById(id);
        if (!r.isEmpty()){
            r.get().setStatus(1);
            this.create(r.get());
        }
    }

    public List<RechargementCompte> getNonValide(){
        return repo.findByStatus(0);
    }

//    Fonction maka somme montant recharge par un utilisateur
    public double getSommeMontantRecharge(Utilisateur u){
        return repo.getSommeMontantRecharge(u);
    }
}
