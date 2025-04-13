package com.rustam.e_commerce.dto.request.create;

import com.rustam.e_commerce.dto.request.read.ProductDiscountedRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CampaignRequest {

    private String name;
    private Long discountPercentage;
    private List<ProductDiscountedRequest> requests;
}
