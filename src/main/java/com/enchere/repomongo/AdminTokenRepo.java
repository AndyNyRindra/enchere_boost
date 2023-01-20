package com.enchere.repomongo;

import com.enchere.model.Admin;
import com.enchere.model.AdminToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdminTokenRepo extends MongoRepository<AdminToken,String> {
//    @Transactional
//    @Modifying
//    @Query(value = "UPDATE admin_token set date_exp=now() where idadmin= :idadmin",nativeQuery = true)
//    public void unvalidOldToken(@Param(value = "idadmin") Long idAdmin);

//    @Query(value = "UPDATE admin_token set date_exp=now()",nativeQuery = true)
//    public void unvalidOldToken();

    public AdminToken findByTokenvalue(String tokenvalue);
}
