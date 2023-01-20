package com.enchere.service;

import com.enchere.model.Admin;
import com.enchere.repomongo.AdminRepo;
import com.enchere.service.common.CrudServiceMongo;
import org.springframework.stereotype.Service;

@Service
public class AdminService extends CrudServiceMongo<Admin, AdminRepo> {
    public AdminService(AdminRepo repo) {
        super(repo);
    }

    public Admin login(String email, String mdpadmin) {
        return repo.findByEmailAndMdp(email, mdpadmin);
    }

}
