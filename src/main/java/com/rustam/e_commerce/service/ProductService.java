package com.rustam.e_commerce.service;

import com.rustam.e_commerce.dao.entity.Category;
import com.rustam.e_commerce.dao.entity.Product;
import com.rustam.e_commerce.dao.repository.ProductRepository;
import com.rustam.e_commerce.dto.request.*;
import com.rustam.e_commerce.dto.response.*;
import com.rustam.e_commerce.exception.custom.NoAuthotiryException;
import com.rustam.e_commerce.mapper.ProductMapper;
import com.rustam.e_commerce.util.UtilService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class ProductService {

    ProductRepository productRepository;
    ProductMapper productMapper;
    UtilService utilService;
    ModelMapper modelMapper;

    public CreateProductResponse create(CreateProductRequest createProductRequest) {
        Category category = utilService.findByCategoryId(createProductRequest.getCategoryId());
        double result = (createProductRequest.getPrice() * createProductRequest.getDiscount()) / 100;
        String currentUsername = utilService.getCurrentUsername();
        Product product = Product.builder()
                .productName(createProductRequest.getProductName())
                .description(createProductRequest.getDescription())
                .quantity(createProductRequest.getQuantity())
                .price(createProductRequest.getPrice())
                .userId(currentUsername)
                .discount(createProductRequest.getDiscount())
                .specialPrice(createProductRequest.getPrice() - result)
                .categoryId(category.getCategoryId())
                .build();
        productRepository.save(product);
        return productMapper.toResponse(product);
    }

    public List<ProductReadResponse> read() {
        List<Product> products = utilService.findAllProduct();
        return productMapper.toResponses(products);
    }

    public ProductUpdateResponse update(ProductUpdateRequest productUpdateRequest) {
        Product product = utilService.findByProductId(productUpdateRequest.getProductId());
        String currentUsername = utilService.getCurrentUsername();
        utilService.validation(product.getUserId(),currentUsername);
        Category category = utilService.findByCategoryId(productUpdateRequest.getCategoryId());
        double result = (productUpdateRequest.getPrice() * productUpdateRequest.getDiscount()) / 100;
        product.setProductName(productUpdateRequest.getProductName());
        product.setPrice(productUpdateRequest.getPrice());
        product.setDiscount(productUpdateRequest.getDiscount());
        product.setQuantity(productUpdateRequest.getQuantity());
        product.setDescription(productUpdateRequest.getDescription());
        product.setSpecialPrice(productUpdateRequest.getPrice() - result);
        product.setCategoryId(category.getCategoryId());
        productRepository.save(product);
        return productMapper.toUpdateResponse(product);
    }


    public ProductDeleteResponse delete(ProductDeleteRequest productDeleteRequest) {
        Product product = utilService.findByProductId(productDeleteRequest.getProductId());
        String currentUsername = utilService.getCurrentUsername();
        utilService.validation(product.getUserId(),currentUsername);
        productRepository.delete(product);
        ProductDeleteResponse productDeleteResponse = new ProductDeleteResponse();
        modelMapper.map(product,productDeleteResponse);
        productDeleteResponse.setText("This product has been deleted by you.");
        return productDeleteResponse;
    }

    public ProductReadResponse readById(ProductReadRequest productReadRequest) {
        String currentUsername = utilService.getCurrentUsername();
        utilService.validation(productReadRequest.getUserId(),currentUsername);
        Product product = utilService.findByProductId(productReadRequest.getProductId());
        return productMapper.toReadResponse(product);
    }

    public ProductQuantityToIncreaseResponse productQuantityToIncrease(ProductQuantityToIncreaseRequest productQuantityToIncreaseRequest) {
        Product product = utilService.findByProductId(productQuantityToIncreaseRequest.getProductId());
        Category category = utilService.findByCategoryId(productQuantityToIncreaseRequest.getCategoryId());
        if (product.getCategoryId().equals(category.getCategoryId())){
            product.setQuantity(product.getQuantity()+productQuantityToIncreaseRequest.getQuantity());
        }else {
            throw new NoAuthotiryException("You do not have the right to increase this product.");
        }
        productRepository.save(product);
        return ProductQuantityToIncreaseResponse.builder()
                .productId(productQuantityToIncreaseRequest.getProductId())
                .productName(product.getProductName())
                .category(category.getCategoryName())
                .quantity(product.getQuantity())
                .text("Your product has been successfully promoted.")
                .build();
    }

    public void save(Product product) {
        productRepository.save(product);
    }
}
