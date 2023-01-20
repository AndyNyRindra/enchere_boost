package com.enchere.repo;

import com.enchere.model.Enchere;
import com.enchere.model.Proposition;
import com.enchere.model.Utilisateur;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface PropositionRepo extends JpaRepository<Proposition, Long> {
    //    Fonction maka ny sommen'ny proposition an'ny utilisateur ray
    @Query(value = "select coalesce(sum(prix),0) from Proposition where utilisateur=:utilisateur and plushaut=true")
    public double getSommeProposition(@Param("utilisateur") Utilisateur utilisateur);

    //    Fonction maka ny plus haut proposition par enchere
    @Query(value = "select coalesce(prix, 0) from Proposition where idenchere=? and plushaut=true", nativeQuery = true)
    public Double getPlusHautProposition(Long idenchere);

    @Query(value = "select * from Proposition where plushaut=true order by prix desc limit 3", nativeQuery = true)
    public List<Proposition> getPlusHautProposition();

    public List<Proposition> findByEnchereAndPlushautTrue(Enchere enchere);

}
