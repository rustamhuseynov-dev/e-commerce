package com.rustam.e_commerce.controller;

import com.rustam.e_commerce.dto.request.UserCreateRequest;
import com.rustam.e_commerce.dto.request.UserUpdateRequest;
import com.rustam.e_commerce.dto.response.UserCreateResponse;
import com.rustam.e_commerce.dto.response.UserDeletedResponse;
import com.rustam.e_commerce.dto.response.UserResponse;
import com.rustam.e_commerce.dto.response.UserUpdateResponse;
import com.rustam.e_commerce.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(path = "/create")
    public ResponseEntity<UserCreateResponse> create(@Valid @RequestBody UserCreateRequest userCreateRequest){
        return new ResponseEntity<>(userService.create(userCreateRequest), HttpStatus.CREATED);
    }

    @GetMapping(path = "/read")
    public ResponseEntity<List<UserResponse>> read(){
        return new ResponseEntity<>(userService.read(),HttpStatus.ACCEPTED);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<UserUpdateResponse> update(@Valid @RequestBody UserUpdateRequest userUpdateRequest){
        return new ResponseEntity<>(userService.update(userUpdateRequest),HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<UserDeletedResponse> delete(@PathVariable UUID id){
        return new ResponseEntity<>(userService.delete(id),HttpStatus.OK);
    }
}
