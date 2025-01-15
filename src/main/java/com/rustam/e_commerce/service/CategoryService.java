package com.rustam.e_commerce.service;

import com.rustam.e_commerce.dao.entity.Category;
import com.rustam.e_commerce.dao.repository.CategoryRepository;
import com.rustam.e_commerce.dto.request.CreateCategoryRequest;
import com.rustam.e_commerce.dto.response.CreateCategoryResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class CategoryService {

    CategoryRepository categoryRepository;

    public CreateCategoryResponse create(CreateCategoryRequest createCategoryRequest) {
        Category category = Category.builder()
                .categoryName(createCategoryRequest.getCategoryName())
                .build();
        categoryRepository.save(category);
        return CreateCategoryResponse.builder().categoryName(createCategoryRequest.getCategoryName()).build();
    }
}
