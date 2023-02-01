package com.enchere.service;

import com.enchere.exception.CustomException;
import com.enchere.model.Categorie;
import com.enchere.model.Enchere;
import com.enchere.model.Photo;
import com.enchere.model.Utilisateur;
import com.enchere.repo.EnchereRepo;
import com.enchere.service.common.CrudService;
import jakarta.persistence.criteria.Predicate;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class EnchereService extends CrudService<Enchere, EnchereRepo> {

    @Autowired
    public DureeEnchereService dureeEnchereService;

    @Autowired
    public CommissionService commissionService;

    @Autowired
    public PhotoService photoService;

    public EnchereService(EnchereRepo repo) {
        super(repo);
    }

    public Enchere create(Enchere enchere) throws CustomException {
        if (enchere.getDuree()==null){
            enchere.setDuree(dureeEnchereService.getLastduree().getDuree());
        } else if (enchere.getCommission()==null) {
            enchere.setCommission(commissionService.getLatestCommission().getPourcentage());
        }
        for(Photo photo:enchere.getPhotos()){
            photo.setId(enchere.getId());
            photoService.create(photo);
        }
        return repo.save(enchere);
    }

    public List<Enchere> getEncheresFinis(Long idutilisateur){
        List<Enchere> enchereList= repo.getEncheresFinis(idutilisateur);
        return enchereList;
    }

    public List<Enchere> getEncheresFinis(){
        List<Enchere> enchereList= repo.getEncheresFinis();
        return enchereList;
    }

    public List<Enchere> findAll(){
        return repo.findAll();
    }

    public List<Enchere> getEnchereByUtilisateur(Utilisateur utilisateur){
        return repo.findByUtilisateur(utilisateur);
    }

    public Double getEncheresEnCours(){
        return repo.getEncheresEnCours();
    }
}
