package com.enchere.service;

import com.enchere.model.DureeEnchere;
import com.enchere.repo.DureeEnchereRepo;
import com.enchere.service.common.CrudService;
import org.springframework.stereotype.Service;

@Service
public class DureeEnchereService extends CrudService<DureeEnchere, DureeEnchereRepo> {
    public DureeEnchereService(DureeEnchereRepo repo) {
        super(repo);
    }
    public DureeEnchere getLastduree(){
        return repo.getLastduree();
    }
}
