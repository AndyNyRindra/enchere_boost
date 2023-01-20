package com.enchere.controller.common;

import com.enchere.exception.CustomException;
import com.enchere.model.HasFK;
import com.enchere.model.HasId;
import com.enchere.service.common.Service;
import com.enchere.service.common.ServiceWithFK;
import com.enchere.util.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.enchere.util.ControllerUtil.returnSuccess;

/*
* How to use:
*   1- Create a controller class that extends this class
*   2- create a service that extends CrudServiceWithFK
*   3- Add @RequestMapping("something/{fkId}/something") annotation to the class
* Then you are good for CRUD operations
*
* use case example:
*   "/books/{fkId}/chapters"
*
* */

public class CrudWithFK <FK, FKS extends Service<FK>, E extends HasFK<FK>, S extends ServiceWithFK<E, FK>> {

    protected final S service;
    protected final FKS fkService;

    public CrudWithFK(S service, FKS fkService) {
        this.service = service;
        this.fkService = fkService;
    }

    @PostMapping("")
    public ResponseEntity<SuccessResponse> create (@PathVariable Long fkId, @RequestBody E obj) throws CustomException {
        FK fk = this.fkService.findById(fkId);
        obj.setFK(fk);
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
    public ResponseEntity<SuccessResponse> findAll(@PathVariable Long fkId) {
        FK fk = this.fkService.findById(fkId);
        return returnSuccess(service.findForFK(fk), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse> update(@PathVariable Long fkId, @PathVariable("id") Long id, @RequestBody E obj) throws CustomException {
        FK fk = this.fkService.findById(fkId);
        obj.setId(id);
        obj.setFK(fk);
        return returnSuccess(service.update(obj), HttpStatus.OK);
    }
}
