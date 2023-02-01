package com.enchere.controller.common;

import com.enchere.exception.CustomException;
import com.enchere.model.Enchere;
import com.enchere.model.Proposition;
import com.enchere.model.Utilisateur;
import com.enchere.model.UtilisateurToken;
import com.enchere.service.PropositionService;
import com.enchere.service.UtilisateurService;
import com.enchere.service.UtilisateurTokenService;
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
public class PropositionController extends CrudController<Proposition, PropositionService> {
    @Autowired
    UtilisateurService utilisateurService;

    @Autowired
    UtilisateurTokenService utilisateurTokenService;

    public PropositionController(PropositionService service) {
        super(service);
    }

    @PostMapping("/user/create")
    public ResponseEntity<?> create2(@RequestHeader(value = "user_token") String token, @RequestBody Proposition proposition) {
        try {
            UtilisateurToken utilisateurToken = utilisateurTokenService.getUserByToken(token);
            Utilisateur utilisateur = utilisateurToken.getUtilisateur();
            double solde = utilisateurService.getSoldeUtilisateur(utilisateur.getId());
            if (solde < proposition.getPrix()) {
                CustomException customException = new CustomException("Solde insuffisant");
                return returnError(customException, HttpStatus.UNAUTHORIZED);
            }
            if (proposition.getPrix() < 0) {
                CustomException customException = new CustomException("Montant invalide");
                return returnError(customException, HttpStatus.UNAUTHORIZED);
            }
            if (utilisateur.equals(proposition.getEnchere().getUtilisateur())) {
                CustomException customException = new CustomException("Ceci est votre propre enchere");
                return returnError(customException, HttpStatus.UNAUTHORIZED);
            }
            List<Proposition> proposition1 = service.getPropByEnchere(proposition.getEnchere());
            if (proposition1 != null) {
                if (proposition1.get(0).getPrix() > proposition.getPrix()) {
                    CustomException customException = new CustomException("Veuillez miser plus haut");
                    return returnError(customException, HttpStatus.UNAUTHORIZED);
                }
            }
            try {
                return returnSuccess(service.create(proposition), HttpStatus.OK);
            } catch (Exception e) {
                return returnError(e, HttpStatus.BAD_REQUEST);
            }
        } catch (CustomException e) {
            return returnError(e, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/somme")
    public ResponseEntity<?> getSomme(Utilisateur u) {

        return returnSuccess(service.getSommeProposition(u), HttpStatus.OK);
    }

    @GetMapping("/enchere/{idenchere}")
    public ResponseEntity<?> getPlusHautEnchereFini(@PathVariable("idenchere") Long idenchere) {
        return returnSuccess(service.getPlushautEnchereFini(idenchere), HttpStatus.OK);
    }

    @GetMapping("/vente/meilleur")
    public ResponseEntity<?> getMeilleurVente() {
        return returnSuccess(service.getPlusHaut(), HttpStatus.OK);
    }

}
