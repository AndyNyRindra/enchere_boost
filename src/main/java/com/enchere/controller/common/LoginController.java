package com.enchere.controller.common;


import com.enchere.exception.LoginException;
import com.enchere.model.LoginEntity;
import com.enchere.service.ServiceLogin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.enchere.util.ControllerUtil.returnSuccess;


public class LoginController <E extends LoginEntity, S extends ServiceLogin<E>> {

    protected S service;

    public LoginController(S service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody E entity) throws LoginException {
        return returnSuccess(service.login(entity), HttpStatus.ACCEPTED);
    }

}
