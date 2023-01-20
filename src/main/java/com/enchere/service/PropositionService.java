package com.enchere.service;

import com.enchere.model.Enchere;
import com.enchere.model.Proposition;
import com.enchere.model.Utilisateur;
import com.enchere.repo.PropositionRepo;
import com.enchere.service.common.CrudService;
import jdk.jshell.execution.Util;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropositionService extends CrudService<Proposition, PropositionRepo> {
    public PropositionService(PropositionRepo repo) {
        super(repo);
    }

    //    Maka ny sommen'ny proposition plushaut rehetra an'ny utilisateur ray
    public double getSommeProposition(Utilisateur u){
        return repo.getSommeProposition(u);
    }

    //    Maka ny plus haut proposition ana utilisateur iray @ enchere efa vita
    public Double getPlushautEnchereFini(Long idenchere){
        Double valiny = repo.getPlusHautProposition(idenchere);
        if(valiny == null){
            valiny = 0.0;
        }
        return valiny;
    }

    public List<Proposition> getPlusHaut(){
        return repo.getPlusHautProposition();
    }

    public List<Proposition> getPropByEnchere(Enchere e){
        List<Proposition> valiny = repo.findByEnchereAndPlushautTrue(e);

        return valiny;
    }
}
