package com.enchere.service;

import com.enchere.model.Admin;
import com.enchere.model.Enchere;
import com.enchere.model.Proposition;
import com.enchere.model.Utilisateur;
import com.enchere.repo.UtilisateurRepo;
import com.enchere.service.common.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurService extends CrudService<Utilisateur, UtilisateurRepo> {
    @Autowired
    PropositionService propositionService;
    @Autowired
    RechargementCompteService rechargementCompteService;

    @Autowired
    EnchereService enchereService;
    public UtilisateurService(UtilisateurRepo repo) {
        super(repo);
    }

    public Utilisateur login(String email, String mdpadmin) {
        return repo.findByEmailAndMdp(email, mdpadmin);
    }

    //    Fonction maka ny solde-na utilisateur iray amin'ny enchere finis rehetra
    public double getSoldeEnchere(Long idutilisateur){
        double solde = 0;
        List<Enchere> liste = enchereService.getEncheresFinis(idutilisateur);

        for (int i = 0; i < liste.size(); i++) {
            List<Proposition> listP = propositionService.getPropByEnchere(liste.get(i));
            for (int j = 0; j < listP.size(); j++) {
                if(listP.get(j).getPrix() != 0.0){
//                    System.out.println("prix proposition :"+listP.get(j).getPrix());
//                    System.out.println("commission : "+listP.get(j).getEnchere().getCommission()/100);
//                    System.out.println("avec commission : "+(listP.get(j).getPrix()
//                            -listP.get(j).getPrix()*listP.get(j).getEnchere().getCommission()/100));
                    solde+= listP.get(j).getPrix()
                            - (listP.get(j).getPrix()*listP.get(j).getEnchere().getCommission()/100);
                }
            }
        }
        return solde;
    }

//    Fonction maka ny solde-na utilisateur iray
    public double getSoldeUtilisateur(Long idutilisateur){
        Utilisateur u = new Utilisateur();
        u.setId(idutilisateur);
        double solde = 0;
        solde = rechargementCompteService.getSommeMontantRecharge(u)- propositionService.getSommeProposition(u) + this.getSoldeEnchere(idutilisateur);
//        System.out.println("rechargement : "+rechargementCompteService.getSommeMontantRecharge(u));
//        System.out.println("proposition deja fait : "+propositionService.getSommeProposition(u));
//        System.out.println("solde enchere : "+this.getSoldeEnchere(idutilisateur));
//        System.out.println("solde : "+solde);
        return solde;
    }
}
