package com.enchere.controller.common;

import com.enchere.exception.CustomException;
import com.enchere.model.Admin;
import com.enchere.model.AdminToken;
import com.enchere.model.Utilisateur;
import com.enchere.model.UtilisateurToken;
import com.enchere.service.EnchereService;
import com.enchere.service.PropositionService;
import com.enchere.service.UtilisateurService;
import com.enchere.service.UtilisateurTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import static com.enchere.util.ControllerUtil.returnError;
import static com.enchere.util.ControllerUtil.returnSuccess;

@RestController
@RequestMapping("/utilisateur")
@CrossOrigin
public class UtilisateurController extends CrudController<Utilisateur, UtilisateurService>{
    @Autowired
    UtilisateurTokenService utilisateurTokenService;

    @Autowired
    EnchereService enchereSerivce;
    @Autowired
    PropositionService propositionService;
    public UtilisateurController(UtilisateurService service) {
        super(service);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Utilisateur utilisateur){
        Utilisateur utilisateur1 = service.login(utilisateur.getEmail(), utilisateur.getMdp());

        if (utilisateur1 != null) {
            utilisateurTokenService.unvalidOldToken(utilisateur1.getId());
            UtilisateurToken utilisateurToken =new UtilisateurToken();
            utilisateurToken.setUtilisateur(utilisateur1);
            utilisateurToken.setTokenvalue(utilisateurToken.generateToken());
            LocalDateTime dateExp=LocalDateTime.now().plusMinutes(60);
            Instant instant=dateExp.toInstant(ZoneOffset.UTC);
            utilisateurToken.setDateExp(Date.from(instant));
            try {
                utilisateurToken=utilisateurTokenService.create(utilisateurToken);
                System.out.println(returnSuccess(utilisateurToken, HttpStatus.OK));
                return returnSuccess(utilisateurToken,HttpStatus.OK);
            } catch (Exception e) {
                return returnError(e, HttpStatus.BAD_REQUEST);
            }
        }
        else{
            CustomException customException=new CustomException("User not found");
            return returnError(customException,HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/token/check")
    private ResponseEntity<?> checkToken(@RequestHeader("user_token") String value){
        UtilisateurToken utilisateurToken=utilisateurTokenService.checkToken(value);
        if(utilisateurToken==null){
            System.out.println(value);
            CustomException customException=new CustomException("Token expired");
            return returnError(customException,HttpStatus.UNAUTHORIZED);
        }
        return returnSuccess(utilisateurToken,HttpStatus.OK);
    }

    @GetMapping("/token/logout")
    private ResponseEntity<?> logout(@RequestHeader("user_token") String token){
//        Admin admin=adminService.getById(id);

        UtilisateurToken utilisateurToken=utilisateurTokenService.getByValue(token);
        if(utilisateurToken==null){
            CustomException customException=new CustomException("Token expired");
            return returnError(customException,HttpStatus.UNAUTHORIZED);
        }
        else{
            utilisateurToken=utilisateurTokenService.logout(utilisateurToken);
            return returnSuccess(utilisateurToken,HttpStatus.OK);
        }
        
    }

    @GetMapping("/solde/{idutilisateur}")
    public ResponseEntity<?> getSoldeUtilisateur(@PathVariable("idutilisateur") Long idutilisateur){
        return returnSuccess(service.getSoldeUtilisateur(idutilisateur),HttpStatus.OK);
    }

    @GetMapping("/solde/token")
    public ResponseEntity<?> getSoldeUtilisateurUsingToken(@RequestHeader("user_token") String token){
        UtilisateurToken utilisateurToken=utilisateurTokenService.checkToken(token);
        if(utilisateurToken==null){
            CustomException customException=new CustomException("Token expired");
            return returnError(customException,HttpStatus.UNAUTHORIZED);
        }
        return returnSuccess(service.getSoldeUtilisateur(utilisateurToken.getUtilisateur().getId()),HttpStatus.OK);
    }
}
