package com.rustam.e_commerce.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductQuantityToIncreaseRequest {
    private Long productId;
    private Integer quantity;
    private Long categoryId;
}
