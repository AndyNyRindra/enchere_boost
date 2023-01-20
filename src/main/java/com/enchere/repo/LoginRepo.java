package com.enchere.repo;

import com.enchere.model.LoginEntity;

import java.util.List;

public interface LoginRepo<E extends LoginEntity> {
    List<E> findByEmail (String email);
}
