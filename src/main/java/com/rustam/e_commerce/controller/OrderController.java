package com.rustam.e_commerce.controller;

import com.rustam.e_commerce.dto.request.OrderCreateRequest;
import com.rustam.e_commerce.dto.response.OrderCreateResponse;
import com.rustam.e_commerce.dto.response.OrderReadResponse;
import com.rustam.e_commerce.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping(path = "/create-order")
    public ResponseEntity<OrderCreateResponse> create(@Valid @RequestBody OrderCreateRequest orderCreateRequest){
        return new ResponseEntity<>(orderService.create(orderCreateRequest), HttpStatus.CREATED);
    }

    @GetMapping(path = "/read-order")
    public ResponseEntity<List<OrderReadResponse>> read(){
        return new ResponseEntity<>(orderService.read(),HttpStatus.OK);
    }
-
}
