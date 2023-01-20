package com.enchere.repo;

import com.enchere.model.Commission;
import com.enchere.model.DureeEnchere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommissionRepo extends JpaRepository<Commission,Long> {
    @Query(value = "SELECT * FROM commission ORDER BY date desc LIMIT 1;",nativeQuery = true)
    public Commission getLastCommission();
}
