package com.rustam.e_commerce.controller;

import com.rustam.e_commerce.dto.request.AdminCreateRequest;
import com.rustam.e_commerce.dto.request.UserCreateRequest;
import com.rustam.e_commerce.dto.request.UserUpdateRequest;
import com.rustam.e_commerce.dto.response.*;
import com.rustam.e_commerce.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping(path = "/create")
    public ResponseEntity<AdminCreateResponse> create(@Valid @RequestBody AdminCreateRequest adminCreateRequest){
        return new ResponseEntity<>(adminService.create(adminCreateRequest), HttpStatus.CREATED);
    }

    @GetMapping(path = "/read-by-id/{id}")
    public ResponseEntity<AdminResponse> readById(@PathVariable UUID id){
        return new ResponseEntity<>(adminService.readById(id),HttpStatus.ACCEPTED);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<UserUpdateResponse> update(@Valid @RequestBody UserUpdateRequest userUpdateRequest){
        return new ResponseEntity<>(adminService.update(userUpdateRequest),HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<UserDeletedResponse> delete(@PathVariable UUID id){
        return new ResponseEntity<>(adminService.delete(id),HttpStatus.OK);
    }
}
