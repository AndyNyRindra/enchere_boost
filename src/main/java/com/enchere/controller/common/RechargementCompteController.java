package com.enchere.controller.common;

import com.enchere.exception.CustomException;
import com.enchere.model.RechargementCompte;
import com.enchere.model.Utilisateur;
import com.enchere.service.RechargementCompteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.enchere.util.ControllerUtil.returnSuccess;

@RestController
@CrossOrigin
@RequestMapping("/rechargement")
public class RechargementCompteController extends CrudController<RechargementCompte,RechargementCompteService>{

    public RechargementCompteController(RechargementCompteService service) {
        super(service);
    }

    @PutMapping("/valider/{id}")
    public void valider(@PathVariable("id") Long id) throws CustomException {
        service.validerRechargement(id);
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
