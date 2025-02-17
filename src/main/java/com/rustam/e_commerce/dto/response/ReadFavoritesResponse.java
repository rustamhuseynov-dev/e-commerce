package com.rustam.e_commerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReadFavoritesResponse {
    private String productName;
    private Integer quantity;
    private String description;
    private String categoryName;
    private Double price;
}
