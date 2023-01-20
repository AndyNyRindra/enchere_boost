package com.enchere.service;

import com.enchere.model.Commission;
import com.enchere.repo.CommissionRepo;
import com.enchere.service.common.CrudService;
import org.springframework.stereotype.Service;

@Service
public class CommissionService extends CrudService<Commission, CommissionRepo> {
    public CommissionService(CommissionRepo repo) {
        super(repo);
    }

    public Commission getLatestCommission(){
        return repo.getLastCommission();
    }
}
