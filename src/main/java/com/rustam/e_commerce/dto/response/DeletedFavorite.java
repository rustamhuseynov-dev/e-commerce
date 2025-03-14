package com.rustam.e_commerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeletedFavorite {
    private UUID userId;
    private String productName;
    private Long productId;
    private Integer quantity;
    private String description;
    private Long categoryId;
    private String categoryName;
    private Double price;
}
