package com.enchere.repo;

import com.enchere.model.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieRepo extends JpaRepository<Categorie,Long> {
}
