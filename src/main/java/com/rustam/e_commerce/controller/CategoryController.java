package com.rustam.e_commerce.controller;

import com.rustam.e_commerce.dto.request.CreateCategoryRequest;
import com.rustam.e_commerce.dto.response.CreateCategoryResponse;
import com.rustam.e_commerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping(path = "/create-category")
    public ResponseEntity<CreateCategoryResponse> create(@RequestBody CreateCategoryRequest createCategoryRequest){
        return new ResponseEntity<>(categoryService.create(createCategoryRequest), HttpStatus.CREATED);
    }
}
