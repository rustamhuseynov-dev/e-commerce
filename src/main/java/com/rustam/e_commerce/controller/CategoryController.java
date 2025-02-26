package com.rustam.e_commerce.controller;

import com.rustam.e_commerce.dto.request.CreateCategoryRequest;
import com.rustam.e_commerce.dto.request.DeleteCategoryRequest;
import com.rustam.e_commerce.dto.request.ReadCategoryRequest;
import com.rustam.e_commerce.dto.request.UpdateCategoryRequest;
import com.rustam.e_commerce.dto.response.CreateCategoryResponse;
import com.rustam.e_commerce.dto.response.DeleteCategoryResponse;
import com.rustam.e_commerce.dto.response.ReadCategoryResponse;
import com.rustam.e_commerce.dto.response.UpdateCategoryResponse;
import com.rustam.e_commerce.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping(path = "/create-category")
    public ResponseEntity<CreateCategoryResponse> create(@Valid @RequestBody CreateCategoryRequest createCategoryRequest){
        return new ResponseEntity<>(categoryService.create(createCategoryRequest), HttpStatus.CREATED);
    }

    @GetMapping(path = "/read-category")
    public ResponseEntity<List<ReadCategoryResponse>> read(){
        return new ResponseEntity<>(categoryService.read(),HttpStatus.OK);
    }

    @GetMapping(path = "/read-category/{name}")
    public ResponseEntity<ReadCategoryResponse> readByName(@PathVariable String name){
        return new ResponseEntity<>(categoryService.readByName(name),HttpStatus.OK);
    }

    @PutMapping(path = "/update-category")
    public ResponseEntity<UpdateCategoryResponse> update(@Valid @RequestBody UpdateCategoryRequest updateCategoryRequest){
        return new ResponseEntity<>(categoryService.update(updateCategoryRequest),HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete-category")
    public ResponseEntity<DeleteCategoryResponse> delete(@Valid @RequestBody DeleteCategoryRequest deleteCategoryRequest){
        return new ResponseEntity<>(categoryService.delete(deleteCategoryRequest),HttpStatus.OK);
    }
}
