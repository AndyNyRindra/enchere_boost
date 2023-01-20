package com.enchere.repo;

import com.enchere.model.UtilisateurToken;
import com.enchere.model.UtilisateurToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UtilisateurTokenRepo extends JpaRepository<UtilisateurToken,Long> {
    @Transactional
    @Modifying
    @Query(value = "UPDATE utilisateur_token set date_exp=now() where idutilisateur= :idutilisateur",nativeQuery = true)
    public void unvalidOldToken(@Param(value = "idutilisateur") Long idutilisateur);

    @Query(value = "SELECT * from Utilisateur_Token order by date_exp desc limit 1",nativeQuery = true)
    public UtilisateurToken findLatest();

    @Query(value = "SELECT * from Utilisateur_Token a where idutilisateur= :idutilisateur and a.date_exp>now()",nativeQuery = true)
    public List<UtilisateurToken> getValidTokenById(@Param(value = "idutilisateur") Integer idUtilisateur);

    @Query(value = "select * from Utilisateur_Token a where a.tokenvalue= :value",nativeQuery = true)
    public UtilisateurToken getTokenByValue(@Param(value = "value") String value);
}
