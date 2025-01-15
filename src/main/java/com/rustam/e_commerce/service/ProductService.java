package com.rustam.e_commerce.service;

import com.rustam.e_commerce.dao.entity.Category;
import com.rustam.e_commerce.dao.entity.Product;
import com.rustam.e_commerce.dao.repository.ProductRepository;
import com.rustam.e_commerce.dto.request.CreateProductRequest;
import com.rustam.e_commerce.dto.response.CreateProductResponse;
import com.rustam.e_commerce.dto.response.ProductReadResponse;
import com.rustam.e_commerce.mapper.ProductMapper;
import com.rustam.e_commerce.util.UtilService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class ProductService {

    ProductRepository productRepository;
    ProductMapper productMapper;
    UtilService utilService;

    public CreateProductResponse create(CreateProductRequest createProductRequest) {
        Category category = utilService.findByCategoryId(createProductRequest.getCategoryId());
        double result = (createProductRequest.getPrice() * createProductRequest.getDiscount()) / 100;
        Product product = Product.builder()
                .productName(createProductRequest.getProductName())
                .description(createProductRequest.getDescription())
                .quantity(createProductRequest.getQuantity())
                .price(createProductRequest.getPrice())
                .discount(createProductRequest.getDiscount())
                .specialPrice(createProductRequest.getPrice() - result)
                .category(category)
                .build();
        productRepository.save(product);
        return productMapper.toResponse(product);
    }

    public List<ProductReadResponse> read() {
        List<Product> products = utilService.findAllProduct();
        return productMapper.toResponses(products);
    }
}
