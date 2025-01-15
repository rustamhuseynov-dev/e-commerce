package com.rustam.e_commerce.controller;

import com.rustam.e_commerce.dto.request.CreateProductRequest;
import com.rustam.e_commerce.dto.response.CreateProductResponse;
import com.rustam.e_commerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping(path = "/create-product")
    public ResponseEntity<CreateProductResponse> create(@RequestBody CreateProductRequest createProductRequest){
        return new ResponseEntity<>(productService.create(createProductRequest), HttpStatus.CREATED);
    }
}
