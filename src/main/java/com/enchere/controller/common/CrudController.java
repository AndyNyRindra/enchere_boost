package com.enchere.controller.common;


import com.enchere.exception.CustomException;
import com.enchere.model.HasId;
import com.enchere.service.common.Service;
import com.enchere.util.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.enchere.util.ControllerUtil.returnSuccess;

/*
* How to use:
*   1- Create a controller class that extends this class
*   2- create a service that extends CrudService
*   3- Add @RequestMapping annotation to the class
* Then you are good for CRUD operations
* */

public class CrudController<E extends HasId, S extends Service<E>> {

    protected final S service;

    public CrudController(S service) {
        this.service = service;
    }

    @PostMapping("")
    public ResponseEntity<SuccessResponse> create(@RequestBody E obj) throws CustomException {
        return returnSuccess(service.create(obj), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse> findById(@PathVariable("id") Long id) {
        return returnSuccess(service.findById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> delete(@PathVariable Long id) {
        service.delete(id);
        return returnSuccess("", HttpStatus.NO_CONTENT);
    }

    @GetMapping("")
    public ResponseEntity<SuccessResponse> findAll() {
        return returnSuccess(service.findAll(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse> update(@PathVariable("id") Long id, @RequestBody E obj) throws CustomException {
        obj.setId(id);
        return returnSuccess(service.update(obj), HttpStatus.OK);
    }

}
