package com.enchere.service;

import com.enchere.model.AdminToken;
import com.enchere.repomongo.AdminTokenRepo;
import com.enchere.service.common.CrudServiceMongo;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class AdminTokenService extends CrudServiceMongo<AdminToken, AdminTokenRepo> {
    public AdminTokenService(AdminTokenRepo repo) {
        super(repo);
    }
    public AdminToken logout(AdminToken adminToken) {
        adminToken.setDateExp(LocalDateTime.now());
        repo.save(adminToken);
        return adminToken;
    }


    public AdminToken getByValue(String value){
        return repo.findByTokenvalue(value);
    }

    public AdminToken checkToken(String value) {
        System.out.println(value);
        return repo.findByTokenvalue(value);
    }

    public void unvalidOldToken(){
        List<AdminToken> adminToken=repo.findAll();
        for(int i=0;i<adminToken.size();i++){
            adminToken.get(i).setDateExp(LocalDateTime.now());
        }
        repo.saveAll(adminToken);
//        repo.unvalidOldToken();
    }

}
