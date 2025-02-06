package com.rustam.e_commerce.dto.response;

import com.rustam.e_commerce.dao.entity.OrderItem;
import com.rustam.e_commerce.dao.entity.Payment;
import com.rustam.e_commerce.dao.entity.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderReadResponse {
    private Long orderId;
    private String email;
    private String userId;
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDate orderDate;
    private Payment payment;

    private Double totalAmount;
    private OrderStatus orderStatus;
    private String text;
}
