package com.rustam.e_commerce.controller;

import com.rustam.e_commerce.dto.request.AdminCreateRequest;
import com.rustam.e_commerce.dto.request.AdminUpdateRequest;
import com.rustam.e_commerce.dto.request.ForAdminRequest;
import com.rustam.e_commerce.dto.response.*;
import com.rustam.e_commerce.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping(path = "/request-admin")
    public ResponseEntity<ForAdminResponse> requestAdmin(){
        return new ResponseEntity<>(adminService.adminRequest(),HttpStatus.OK);
    }

    @PostMapping(path = "/create")
    public ResponseEntity<AdminCreateResponse> create(@Valid @RequestBody AdminCreateRequest adminCreateRequest){
        return new ResponseEntity<>(adminService.create(adminCreateRequest), HttpStatus.CREATED);
    }

    @GetMapping(path = "/read-by-id")
    public ResponseEntity<AdminResponse> readById(){
        return new ResponseEntity<>(adminService.readById(),HttpStatus.ACCEPTED);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<AdminUpdateResponse> update(@Valid @RequestBody AdminUpdateRequest adminUpdateRequest){
        return new ResponseEntity<>(adminService.update(adminUpdateRequest),HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<AdminDeleteResponse> delete(){
        return new ResponseEntity<>(adminService.delete(),HttpStatus.OK);
    }
}
