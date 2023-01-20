package com.enchere.controller.common;

import com.enchere.model.Commission;
import com.enchere.service.CommissionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.enchere.util.ControllerUtil.returnSuccess;

@RestController
@RequestMapping("/commission")
@CrossOrigin
public class CommissionController extends CrudController<Commission, CommissionService> {
    public CommissionController(CommissionService service) {
        super(service);
    }

    @GetMapping("/latest")
    public ResponseEntity<?> getLast(){
        return returnSuccess(service.getLatestCommission(), HttpStatus.OK);
    }
}
