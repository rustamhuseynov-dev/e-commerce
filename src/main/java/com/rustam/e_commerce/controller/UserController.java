package com.rustam.e_commerce.controller;

import com.rustam.e_commerce.dto.request.UserCreateRequest;
import com.rustam.e_commerce.dto.response.UserCreateResponse;
import com.rustam.e_commerce.dto.response.UserResponse;
import com.rustam.e_commerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(path = "/create")
    public ResponseEntity<UserCreateResponse> create(@RequestBody UserCreateRequest userCreateRequest){
        return new ResponseEntity<>(userService.create(userCreateRequest), HttpStatus.CREATED);
    }

    @GetMapping(path = "/read")
    public ResponseEntity<List<UserResponse>> read(){
        return new ResponseEntity<>(userService.read(),HttpStatus.ACCEPTED);
    }
}
