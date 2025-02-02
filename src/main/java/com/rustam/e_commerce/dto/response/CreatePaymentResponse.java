package com.rustam.e_commerce.dto.response;

import com.rustam.e_commerce.dao.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePaymentResponse {
    private Long paymentId;
    private Order order;
    private String paymentMethod;
}
