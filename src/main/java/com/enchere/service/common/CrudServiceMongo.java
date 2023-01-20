package com.enchere.service.common;

import com.enchere.exception.CustomException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public class CrudServiceMongo<E,  R extends MongoRepository<E, String>> implements Service<E> {

    protected final R repo;

    public CrudServiceMongo(R repo) {
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
        repo.deleteById(String.valueOf(id));
    }

    @Override
    public E findById(Object id) {
        return repo.findById(String.valueOf(id)).orElse(null);
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
