package com.enchere.repomongo;

import com.enchere.model.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminRepo extends MongoRepository<Admin,String> {
    public Admin findByEmailAndMdp(String email,String mdp);
}
