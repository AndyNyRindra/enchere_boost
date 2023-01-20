package com.enchere.controller.common;


import com.enchere.exception.CustomException;
import com.enchere.model.Admin;
import com.enchere.model.AdminToken;
import com.enchere.service.AdminService;
import com.enchere.service.AdminTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

import static com.enchere.util.ControllerUtil.returnError;
import static com.enchere.util.ControllerUtil.returnSuccess;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController extends CrudControllerMongo<Admin, AdminService> {
    @Autowired
    AdminTokenService adminTokenService;

    public AdminController(AdminService service) {
        super(service);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Admin admin){
        System.out.println(admin.getEmail());
        Admin admin1 = service.login(admin.getEmail(), admin.getMdp());

        if (admin1 != null) {
            adminTokenService.unvalidOldToken();
            AdminToken adminToken =new AdminToken();
            adminToken.setTokenvalue(adminToken.generateToken());
            LocalDateTime dateExp=LocalDateTime.now().plusMinutes(60);
            Instant instant=dateExp.toInstant(ZoneOffset.UTC);
            adminToken.setDateExp(LocalDateTime.now());
//            adminToken.setDateExp(Date.from(instant));
            try {
                adminToken=adminTokenService.create(adminToken);
                System.out.println(returnSuccess(adminToken,HttpStatus.OK));
                return returnSuccess(adminToken,HttpStatus.OK);
            } catch (Exception e) {
                return returnError(e, HttpStatus.BAD_REQUEST);
            }
        }
        else{
            CustomException customException=new CustomException("Admin not found");
            return returnError(customException,HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/token/check")
    private ResponseEntity<?> checkToken(@RequestHeader("admin_token") String value){
        AdminToken adminToken=adminTokenService.checkToken(value);
        System.out.println(adminToken);
        if(adminToken==null){
            System.out.println(value);
            CustomException customException=new CustomException("Token expired");
            return returnError(customException,HttpStatus.UNAUTHORIZED);
        }
        return returnSuccess(adminToken,HttpStatus.OK);
    }

    @GetMapping("/token/logout")
    private ResponseEntity<?> logout(@RequestHeader("admin_token") String token){
//        Admin admin=adminService.getById(id);

        AdminToken adminToken=adminTokenService.getByValue(token);
        if(adminToken==null){
            CustomException customException=new CustomException("Token expired");
            return returnError(customException,HttpStatus.UNAUTHORIZED);
        }
        else{
            adminToken=adminTokenService.logout(adminToken);
            return returnSuccess(adminToken,HttpStatus.OK);
        }

    }
}
