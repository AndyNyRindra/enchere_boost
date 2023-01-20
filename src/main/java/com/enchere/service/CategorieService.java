package com.enchere.service;

import com.enchere.model.Categorie;
import com.enchere.repo.CategorieRepo;
import com.enchere.service.common.CrudService;
import org.springframework.stereotype.Service;

@Service
public class CategorieService extends CrudService<Categorie, CategorieRepo> {
    public CategorieService(CategorieRepo repo) {
        super(repo);
    }
}
