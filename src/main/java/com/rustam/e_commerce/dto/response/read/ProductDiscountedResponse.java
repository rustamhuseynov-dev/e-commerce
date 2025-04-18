package com.rustam.e_commerce.dto.response.read;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDiscountedResponse {
    private Long productId;
    private String productName;
    private String description;
    private Integer quantity;
    private double price;
    private double discount;
    private double specialPrice;
}
