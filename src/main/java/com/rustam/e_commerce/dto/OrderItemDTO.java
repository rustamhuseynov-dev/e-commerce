package com.rustam.e_commerce.dto;

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
public class OrderItemDTO {
    private String userId;
    private List<OrderItem> orderItems = new ArrayList<>();
    private Payment payment;
    private LocalDate orderDate;
    private Double totalAmount;
    private OrderStatus orderStatus;
}
