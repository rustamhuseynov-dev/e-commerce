package com.rustam.e_commerce.controller;

import com.rustam.e_commerce.dto.request.*;
import com.rustam.e_commerce.dto.response.*;
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

    @GetMapping(path = "/read-by-id/{id}")
    public ResponseEntity<ProductReadResponse> readById(@RequestBody ProductReadRequest productReadRequest){
        return new ResponseEntity<>(productService.readById(productReadRequest),HttpStatus.OK);
    }

    @PutMapping(path = "/update-product")
    public ResponseEntity<ProductUpdateResponse> update(@RequestBody ProductUpdateRequest productUpdateRequest){
        return new ResponseEntity<>(productService.update(productUpdateRequest),HttpStatus.OK);
    }

    @PutMapping(path = "/product-quantity-to-increase")
    public ResponseEntity<ProductQuantityToIncreaseResponse> productQuantityToIncrease(@RequestBody ProductQuantityToIncreaseRequest productQuantityToIncreaseRequest ){
        return new ResponseEntity<>(productService.productQuantityToIncrease(productQuantityToIncreaseRequest),HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete-product")
    public ResponseEntity<ProductDeleteResponse> delete(@RequestBody ProductDeleteRequest productDeleteRequest){
        return new ResponseEntity<>(productService.delete(productDeleteRequest),HttpStatus.OK);
    }

}
