package com.enchere.controller.common;

import com.enchere.exception.CustomException;
import com.enchere.model.RechargementCompte;
import com.enchere.model.Utilisateur;
import com.enchere.model.UtilisateurToken;
import com.enchere.service.RechargementCompteService;
import com.enchere.service.UtilisateurService;
import com.enchere.service.UtilisateurTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.enchere.util.ControllerUtil.returnError;
import static com.enchere.util.ControllerUtil.returnSuccess;

@RestController
@CrossOrigin
@RequestMapping("/rechargement")
public class RechargementCompteController extends CrudController<RechargementCompte,RechargementCompteService>{

    @Autowired
    UtilisateurService utilisateurService;

    @Autowired
    UtilisateurTokenService utilisateurTokenService;

    public RechargementCompteController(RechargementCompteService service) {
        super(service);
    }

    @PutMapping("/valider/{id}")
    public void valider(@PathVariable("id") Long id) throws CustomException {
        service.validerRechargement(id);
    }

    @PostMapping("/new")
    public ResponseEntity<?> create(@RequestHeader(value="user_token") String token,@RequestBody RechargementCompte rechargementCompte){
        try{
            UtilisateurToken utilisateurToken=utilisateurTokenService.getUserByToken(token);
            rechargementCompte.setUtilisateur(utilisateurToken.getUtilisateur());
            if(rechargementCompte.getMontant()<=0){
                CustomException customException=new CustomException("Montant invalide");
                return returnError(customException,HttpStatus.FORBIDDEN);
            }
            rechargementCompte=service.create(rechargementCompte);
            return returnSuccess(rechargementCompte,HttpStatus.OK);
        }catch (Exception e){
            return returnError(e,HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/nonvalide/list")
    public ResponseEntity<?> getNonValide(){
        return returnSuccess(service.getNonValide(),HttpStatus.OK);
    }

    @GetMapping("/somme")
    public ResponseEntity<?> getSomme(Utilisateur utilisateur){

        return returnSuccess(service.getSommeMontantRecharge(utilisateur), HttpStatus.OK);
    }
}
