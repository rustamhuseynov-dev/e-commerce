package com.rustam.e_commerce.dto.response;

import com.rustam.e_commerce.dao.entity.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrackOrderResponse {
    private OrderStatus orderStatus;
    private String productName;
    private Integer quantity;
    private Double totalAmount;
    private String shippingAddress;
}
