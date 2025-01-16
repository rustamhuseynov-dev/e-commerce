package com.rustam.e_commerce.dto.response;

import com.rustam.e_commerce.dao.entity.Category;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateProductResponse {
    private String productName;
    private String description;
    private Integer quantity;
    private String userId;
    private double price;
    private double discount;
    private double specialPrice;
    private Long category;
}
