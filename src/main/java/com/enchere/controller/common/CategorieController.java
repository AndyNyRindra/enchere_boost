package com.enchere.controller.common;

import com.enchere.model.Categorie;
import com.enchere.service.CategorieService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/categories")
@RestController
@CrossOrigin
public class CategorieController extends CrudController<Categorie, CategorieService>{
    public CategorieController(CategorieService service) {
        super(service);
    }
}
