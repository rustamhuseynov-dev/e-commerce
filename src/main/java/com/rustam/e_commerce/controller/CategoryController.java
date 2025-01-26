package com.rustam.e_commerce.controller;

import com.rustam.e_commerce.dto.request.CreateCategoryRequest;
import com.rustam.e_commerce.dto.request.ReadCategoryRequest;
import com.rustam.e_commerce.dto.request.UpdateCategoryRequest;
import com.rustam.e_commerce.dto.response.CreateCategoryResponse;
import com.rustam.e_commerce.dto.response.ReadCategoryResponse;
import com.rustam.e_commerce.dto.response.UpdateCategoryResponse;
import com.rustam.e_commerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping(path = "/create-category")
    public ResponseEntity<CreateCategoryResponse> create(@RequestBody CreateCategoryRequest createCategoryRequest){
        return new ResponseEntity<>(categoryService.create(createCategoryRequest), HttpStatus.CREATED);
    }

    @PutMapping(path = "/update-category")
    public ResponseEntity<UpdateCategoryResponse> update(@RequestBody UpdateCategoryRequest updateCategoryRequest){
        return new ResponseEntity<>(categoryService.update(updateCategoryRequest),HttpStatus.OK);
    }

    @GetMapping(path = "/read-category")
    public ResponseEntity<ReadCategoryResponse> read(@RequestBody ReadCategoryRequest readCategoryRequest){
        return new ResponseEntity<>(categoryService.read(readCategoryRequest),HttpStatus.OK);
    }
}
