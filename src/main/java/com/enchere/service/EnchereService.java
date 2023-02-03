package com.enchere.service;

import com.enchere.exception.CustomException;
import com.enchere.model.Enchere;
import com.enchere.model.Photo;
import com.enchere.model.Utilisateur;
import com.enchere.repo.EnchereRepo;
import com.enchere.service.common.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        List<Photo> photos= enchere.getPhotos();
        enchere=repo.save(enchere);
        if(photos!=null){
            for(Photo photo:photos){
                photo.setIdenchere(enchere.getId());
                photoService.create(photo);
            }
        }
        return enchere;
//        for(Photo photo:enchere.getPhotos()){
//            photo.setId(enchere.getId());
//            photoService.create(photo);
//        }
//        return repo.save(enchere);
    }

    public List<Enchere> getEncheresFinis(Long idutilisateur){
        List<Enchere> enchereList= repo.getEncheresFinis(idutilisateur);
        return enchereList;
    }

    public List<Enchere> getEncheresFinis(){
        List<Enchere> enchereList= repo.getEncheresFinis();
        return enchereList;
    }

    public Enchere getById(Long id) throws CustomException {
        Optional<Enchere> enchere=repo.findById(id);
        if(enchere.isPresent()){
            return enchere.get();
        }
        else{
            throw new CustomException("Enchere not found");
        }
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
