package com.rustam.e_commerce.dto.request;

import com.rustam.e_commerce.dao.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateProductRequest {
    private String productName;
    private String description;
    private Integer quantity;
    private double price;
    private double discount;
    private double specialPrice;
    private Long categoryId;
}
