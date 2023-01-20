package com.enchere.service.common;

import com.enchere.exception.CustomException;

import java.util.List;

public interface Service <E> {

    E create(E obj) throws CustomException;

    E update(E obj) throws CustomException;

    void delete(Object id);

    E findById(Object id);

    Iterable<E> findAll();
    List<E> saveAll(List<E> obj);

}
