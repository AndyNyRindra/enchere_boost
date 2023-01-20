package com.enchere.controller.common;

import com.enchere.model.DureeEnchere;
import com.enchere.service.DureeEnchereService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.enchere.util.ControllerUtil.returnSuccess;

@RestController
@RequestMapping("/dureeEnchere")
@CrossOrigin
public class DureeEnchereController extends CrudController<DureeEnchere, DureeEnchereService>{
    public DureeEnchereController(DureeEnchereService service) {
        super(service);
    }

    @GetMapping("/latest")
    public ResponseEntity<?> getLast(){
        return returnSuccess(service.getLastduree(), HttpStatus.OK);
    }
}
