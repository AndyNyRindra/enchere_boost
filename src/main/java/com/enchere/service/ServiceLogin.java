package com.enchere.service;

import com.enchere.exception.LoginException;
import com.enchere.model.LoginEntity;

public interface ServiceLogin<E extends LoginEntity> {
    E login(E entity) throws LoginException;
}
