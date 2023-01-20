package com.enchere.controller.common;

import com.enchere.exception.CustomException;
import com.enchere.model.Enchere;
import com.enchere.model.Proposition;
import com.enchere.model.Utilisateur;
import com.enchere.service.PropositionService;
import com.enchere.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.enchere.util.ControllerUtil.returnError;
import static com.enchere.util.ControllerUtil.returnSuccess;

@RestController
@CrossOrigin
@RequestMapping("/propositions")
public class PropositionController extends CrudController<Proposition,PropositionService>{
    @Autowired
    UtilisateurService utilisateurService;

    public PropositionController(PropositionService service) {
        super(service);
    }

    @PostMapping("/user/create")
    public ResponseEntity<?> create2(@RequestBody Proposition proposition) {
        Utilisateur utilisateur=utilisateurService.findById(proposition.getUtilisateur().getId());
        double solde=utilisateurService.getSoldeUtilisateur(proposition.getUtilisateur().getId());
        if(solde<proposition.getPrix()){
            CustomException customException=new CustomException("Solde insuffisant");
            return returnError(customException,HttpStatus.UNAUTHORIZED);
        }
        if(utilisateur.equals(proposition.getEnchere().getUtilisateur())){
            CustomException customException=new CustomException("Ceci est votre propre enchere");
            return returnError(customException,HttpStatus.UNAUTHORIZED);
        }
        List<Proposition> proposition1=service.getPropByEnchere(proposition.getEnchere());
        if(proposition1!=null){
            if(proposition1.get(0).getPrix()>proposition.getPrix()){
                CustomException customException=new CustomException("Veuillez miser plus haut");
                return returnError(customException,HttpStatus.UNAUTHORIZED);
            }
        }
        try {
            return returnSuccess(service.create(proposition), HttpStatus.OK);
        }
        catch (Exception e){
            return returnError(e,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/somme")
    public ResponseEntity<?> getSomme(Utilisateur u){

        return returnSuccess(service.getSommeProposition(u),HttpStatus.OK);
    }

    @GetMapping("/enchere/{idenchere}")
    public ResponseEntity<?> getPlusHautEnchereFini(@PathVariable("idenchere") Long idenchere){
        return returnSuccess(service.getPlushautEnchereFini(idenchere), HttpStatus.OK);
    }

    @GetMapping("/vente/meilleur")
    public ResponseEntity<?> getMeilleurVente(){
        return returnSuccess(service.getPlusHaut(), HttpStatus.OK);
    }

}
