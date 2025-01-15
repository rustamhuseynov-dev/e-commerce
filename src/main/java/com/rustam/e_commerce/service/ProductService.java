package com.rustam.e_commerce.service;

import com.rustam.e_commerce.dao.entity.Category;
import com.rustam.e_commerce.dao.entity.Product;
import com.rustam.e_commerce.dao.repository.ProductRepository;
import com.rustam.e_commerce.dto.request.CreateProductRequest;
import com.rustam.e_commerce.dto.response.CreateProductResponse;
import com.rustam.e_commerce.mapper.ProductMapper;
import com.rustam.e_commerce.util.UtilService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class ProductService {

    ProductRepository productRepository;
    ProductMapper productMapper;
    UtilService utilService;

    public CreateProductResponse create(CreateProductRequest createProductRequest) {
        Category category = utilService.findByCategoryId(createProductRequest.getCategoryId());
        Product product = Product.builder()
                .productName(createProductRequest.getProductName())
                .description(createProductRequest.getDescription())
                .quantity(createProductRequest.getQuantity())
                .price(createProductRequest.getPrice())
                .discount(createProductRequest.getDiscount())
                .specialPrice(createProductRequest.getSpecialPrice())
                .category(category)
                .build();
        productRepository.save(product);
        return productMapper.toResponse(product);
    }
}
