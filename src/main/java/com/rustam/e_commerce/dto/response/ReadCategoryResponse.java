package com.rustam.e_commerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReadCategoryResponse {
    private Long productId;

    private String productName;

    private String description;

    private Integer quantity;
    private double price;
    private double discount;
    private double specialPrice;
    private String userId;
    private String imageUrl;
    private String videoUrl;
}
