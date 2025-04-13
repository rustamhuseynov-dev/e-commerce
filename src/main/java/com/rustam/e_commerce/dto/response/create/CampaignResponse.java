package com.rustam.e_commerce.dto.response.create;

import com.rustam.e_commerce.dao.entity.Product;
import com.rustam.e_commerce.dto.response.CreateProductResponse;
import com.rustam.e_commerce.dto.response.ProductReadResponse;
import com.rustam.e_commerce.dto.response.read.ProductDiscountedResponse;
import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CampaignResponse {

    private Long id;
    private String name;
    private Long discountPercentage;
    private List<ProductDiscountedResponse> responses;
}
