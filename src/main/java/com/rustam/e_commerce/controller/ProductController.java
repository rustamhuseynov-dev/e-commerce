package com.rustam.e_commerce.controller;

import com.rustam.e_commerce.dto.request.CreateProductRequest;
import com.rustam.e_commerce.dto.request.ProductUpdateRequest;
import com.rustam.e_commerce.dto.response.CreateProductResponse;
import com.rustam.e_commerce.dto.response.ProductReadResponse;
import com.rustam.e_commerce.dto.response.ProductUpdateResponse;
import com.rustam.e_commerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping(path = "/create-product")
    public ResponseEntity<CreateProductResponse> create(@RequestBody CreateProductRequest createProductRequest){
        return new ResponseEntity<>(productService.create(createProductRequest), HttpStatus.CREATED);
    }

    @GetMapping(path = "/read-product")
    public ResponseEntity<List<ProductReadResponse>> read(){
        return new ResponseEntity<>(productService.read(),HttpStatus.OK);
    }

    @PutMapping(path = "/update-product")
    public ResponseEntity<ProductUpdateResponse> update(@RequestBody ProductUpdateRequest productUpdateRequest){
        return new ResponseEntity<>(productService.update(productUpdateRequest),HttpStatus.OK);
    }
    

}
