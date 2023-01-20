package com.enchere.repo;

import com.enchere.model.DureeEnchere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DureeEnchereRepo extends JpaRepository<DureeEnchere,Long> {
    @Query(value = "SELECT * FROM duree_Enchere ORDER BY date desc LIMIT 1;",nativeQuery = true)
    public DureeEnchere getLastduree();
}
