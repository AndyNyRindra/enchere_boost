package com.enchere.controller.common;

import com.enchere.exception.CustomException;
import com.enchere.model.*;
import com.enchere.recherche.ConstructionRequete;
import com.enchere.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.sql.Date;
import java.util.List;

import static com.enchere.util.ControllerUtil.returnError;
import static com.enchere.util.ControllerUtil.returnSuccess;

@RequestMapping("/encheres")
@RestController
@CrossOrigin
public class EnchereController extends CrudController<Enchere, EnchereService>{
    @Autowired
    UtilisateurService utilisateurService;

    @Autowired
    UtilisateurTokenService utilisateurTokenService;

    @Autowired
    PropositionService propositionService;

    public EnchereController(EnchereService service) {
        super(service);
    }

    @Autowired
    RechercheEnchere rechercheEnchere;

    @GetMapping("/finis/{id}")
    public ResponseEntity<?> getEncheresFinis(@PathVariable("id") Long idutilisateur) throws CustomException {
        return returnSuccess(service.getEncheresFinis(idutilisateur), HttpStatus.OK);
    }

    @GetMapping("/finis/token")
    public ResponseEntity<?> getEncheresFinisUsingFini(@RequestHeader(value="user_token") String token) {
        UtilisateurToken utilisateurToken= null;
        try {
            utilisateurToken = utilisateurTokenService.getUserByToken(token);
        } catch (CustomException e) {
            return returnError(e, HttpStatus.UNAUTHORIZED);
        }
        Long idutilisateur=utilisateurToken.getUtilisateur().getId();
        return returnSuccess(service.getEncheresFinis(idutilisateur), HttpStatus.OK);
    }

    @GetMapping("/rechercheavancee")
    public ResponseEntity<?> filtrer(@RequestBody ConstructionRequete constructionRequete){
        return returnSuccess(rechercheEnchere.rechercheAvancee(constructionRequete), HttpStatus.OK);
    }

    @GetMapping("/recherchemotcle/{key}")
    public ResponseEntity<?> filtrerMotCle(@PathVariable("key") String key){
        return returnSuccess(rechercheEnchere.rechercheMotCle(key), HttpStatus.OK);
    }

    @GetMapping("/utilisateur/{id}")
    public ResponseEntity<?> getForUtilisateur(@PathVariable("id") Long id){
        Utilisateur utilisateur=utilisateurService.findById(id);
        return returnSuccess(service.getEnchereByUtilisateur(utilisateur), HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<?> createWithToken(@RequestHeader(value = "user_token") String token,@RequestBody Enchere enchere){
        try{
            UtilisateurToken utilisateurToken=utilisateurTokenService.getUserByToken(token);
            Utilisateur utilisateur=utilisateurToken.getUtilisateur();
            enchere.setUtilisateur(utilisateur);
            return returnSuccess(service.create(enchere), HttpStatus.OK);
        }
        catch (Exception e){
            return returnError(e,HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/fiche/{id}")
    public ResponseEntity<?> getFiche(@PathVariable("id") Long id){
        try {
            Enchere enchere=service.getById(id);
            List<Proposition> propositions=propositionService.getPropByEnchere(enchere);
            if(propositions.size()==0){
                PropHaut propHaut=new PropHaut();
                propHaut.setPrix(0.0);
                propHaut.setUtilisateur(null);
                enchere.setPlusHaut(propHaut);
            }
            else {
                PropHaut propHaut=new PropHaut();
                propHaut.setPrix(propositions.get(0).getPrix());
                propHaut.setUtilisateur(propositions.get(0).getUtilisateur());
                enchere.setPlusHaut(propHaut);
            }
            return returnSuccess(enchere,HttpStatus.OK);
        } catch (CustomException e) {
            return returnError(e,HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/get/token")
    public ResponseEntity<?> getForUtilisateurToken(@RequestHeader(value="user_token") String token){
        try{
            UtilisateurToken utilisateurToken=utilisateurTokenService.getUserByToken(token);
            Utilisateur utilisateur=utilisateurToken.getUtilisateur();
            return returnSuccess(service.getEnchereByUtilisateur(utilisateur), HttpStatus.OK);
        }
        catch (Exception e){
            return returnError(e,HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/dureemoyenne")
    public ResponseEntity<?> getDureeMoyenne(){
        List<Enchere> liste = service.findAll();
        DureeMoyenne dureeMoyenne = new DureeMoyenne(liste);
        return returnSuccess(dureeMoyenne.getDuree(), HttpStatus.OK);
    }

    @GetMapping("/encours")
    public ResponseEntity<?> getEnCours(){
        return returnSuccess(service.getEncheresEnCours(), HttpStatus.OK);
    }
}
