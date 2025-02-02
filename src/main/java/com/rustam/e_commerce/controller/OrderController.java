package com.rustam.e_commerce.controller;

import com.rustam.e_commerce.dto.request.OrderCreateRequest;
import com.rustam.e_commerce.dto.response.OrderCreateResponse;
import com.rustam.e_commerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping(path = "/create-order")
    public ResponseEntity<OrderCreateResponse> create(@RequestBody OrderCreateRequest orderCreateRequest){
        return new ResponseEntity<>(orderService.create(orderCreateRequest), HttpStatus.CREATED);
    }
}
