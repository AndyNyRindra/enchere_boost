package com.enchere.repo;

import com.enchere.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepo extends JpaRepository<Photo,Long> {
}
