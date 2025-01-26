package com.rustam.e_commerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductQuantityToIncreaseResponse {
    private Long productId;
    private String productName;
    private Integer quantity;
    private String category;
    private String text;
}
