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
    public ResponseEntity<ForAdminResponse> requestAdmin(@RequestBody ForAdminRequest forAdminRequest){
        return new ResponseEntity<>(adminService.adminRequest(forAdminRequest),HttpStatus.OK);
    }

    @PostMapping(path = "/create")
    public ResponseEntity<AdminCreateResponse> create(@Valid @RequestBody AdminCreateRequest adminCreateRequest){
        return new ResponseEntity<>(adminService.create(adminCreateRequest), HttpStatus.CREATED);
    }

    @GetMapping(path = "/read-by-id/{id}")
    public ResponseEntity<AdminResponse> readById(@PathVariable UUID id){
        return new ResponseEntity<>(adminService.readById(id),HttpStatus.ACCEPTED);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<AdminUpdateResponse> update(@Valid @RequestBody AdminUpdateRequest adminUpdateRequest){
        return new ResponseEntity<>(adminService.update(adminUpdateRequest),HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<AdminDeleteResponse> delete(@PathVariable UUID id){
        return new ResponseEntity<>(adminService.delete(id),HttpStatus.OK);
    }
}
