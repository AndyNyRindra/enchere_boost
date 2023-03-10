package com.enchere.service;

import com.enchere.exception.CustomException;
import com.enchere.model.AdminToken;
import com.enchere.model.Utilisateur;
import com.enchere.model.UtilisateurToken;
import com.enchere.repo.UtilisateurTokenRepo;
import com.enchere.service.common.CrudService;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class UtilisateurTokenService extends CrudService<UtilisateurToken, UtilisateurTokenRepo> {

    public UtilisateurTokenService(UtilisateurTokenRepo repo) {
        super(repo);
    }

    public UtilisateurToken logout(UtilisateurToken utilisateurToken) {
        utilisateurToken.setDateExp(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)));
        repo.save(utilisateurToken);
        return utilisateurToken;
    }

    public List<UtilisateurToken> getValidToken(Integer idUtilisateur) {
        return repo.getValidTokenById(idUtilisateur);
    }

    public UtilisateurToken getByValue(String value){
        return repo.getTokenByValue(value);
    }

    public UtilisateurToken getUserByToken(String token) throws CustomException {
        if(token==null){
            throw new CustomException("Token invalid");
        }
        UtilisateurToken utilisateurToken= repo.getUtilisateurTokenByTokenvalueAndDateExpGreaterThan(token,Date.valueOf(LocalDate.now()));
        if(utilisateurToken==null){
            throw new CustomException("Token invalid");
        }
        return utilisateurToken;
    }



    public UtilisateurToken checkToken(String value) {
        System.out.println(value);
        return repo.getTokenByValue(value);
    }

    public void unvalidOldToken(Long idUser){
        repo.unvalidOldToken(idUser);
    }

}
