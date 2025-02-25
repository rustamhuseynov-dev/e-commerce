package com.rustam.e_commerce.service;

import com.rustam.e_commerce.dao.entity.Category;
import com.rustam.e_commerce.dao.repository.CategoryRepository;
import com.rustam.e_commerce.dto.request.CreateCategoryRequest;
import com.rustam.e_commerce.dto.request.DeleteCategoryRequest;
import com.rustam.e_commerce.dto.request.ReadCategoryRequest;
import com.rustam.e_commerce.dto.request.UpdateCategoryRequest;
import com.rustam.e_commerce.dto.response.CreateCategoryResponse;
import com.rustam.e_commerce.dto.response.DeleteCategoryResponse;
import com.rustam.e_commerce.dto.response.ReadCategoryResponse;
import com.rustam.e_commerce.dto.response.UpdateCategoryResponse;
import com.rustam.e_commerce.mapper.CategoryMapper;
import com.rustam.e_commerce.util.UtilService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class CategoryService {

    CategoryRepository categoryRepository;
    UtilService utilService;
    CategoryMapper categoryMapper;

    public CreateCategoryResponse create(CreateCategoryRequest createCategoryRequest) {
        Category category = Category.builder()
                .categoryName(createCategoryRequest.getCategoryName())
                .build();
        categoryRepository.save(category);
        return CreateCategoryResponse.builder().categoryName(createCategoryRequest.getCategoryName()).build();
    }

    public UpdateCategoryResponse update(UpdateCategoryRequest updateCategoryRequest) {
        Category category = utilService.findByCategoryId(updateCategoryRequest.getCategoryId());
        category.setCategoryName(updateCategoryRequest.getCategoryName());
        categoryRepository.save(category);
        return UpdateCategoryResponse.builder()
                .categoryId(updateCategoryRequest.getCategoryId())
                .categoryName(updateCategoryRequest.getCategoryName()).build();
    }

    public List<ReadCategoryResponse> read() {
        List<Category> categories = categoryRepository.findAll();
        return categoryMapper.toRead(categories);
    }

    public DeleteCategoryResponse delete(DeleteCategoryRequest deleteCategoryRequest) {
        Category category = utilService.findByCategoryId(deleteCategoryRequest.getCategoryId());
        categoryRepository.delete(category);
        return DeleteCategoryResponse.builder()
                .categoryId(deleteCategoryRequest.getCategoryId())
                .categoryName(category.getCategoryName())
                .text("this category has been deleted")
                .build();
    }
}
