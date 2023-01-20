package com.enchere.service.common;

import com.enchere.exception.CustomException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public class CrudService <E,  R extends JpaRepository<E, Long>> implements Service<E> {

    protected final R repo;

    public CrudService(R repo) {
        this.repo = repo;
    }

    @Override
    public E create(E obj) throws CustomException {
        return repo.save(obj);
    }

    @Override
    public E update(E obj) throws CustomException {
        return repo.save(obj);
    }

    @Override
    public void delete(Object id) {
        repo.deleteById((Long) id);
    }

    @Override
    public E findById(Object id) {
        return repo.findById((Long) id).orElse(null);
    }

    @Override
    public Iterable<E> findAll() {
        return repo.findAll();
    }
    @Override
    public List<E>saveAll(List<E> iterable){
        return repo.saveAll(iterable);        
    }

}
