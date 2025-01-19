package com.rustam.e_commerce.dto.response;

import com.rustam.e_commerce.dao.entity.OrderItem;
import com.rustam.e_commerce.dao.entity.Payment;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
public class OrderCreateResponse {
    private String email;

    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDate orderDate;
    private Payment payment;

    private Double totalAmount;
    private String orderStatus;
}
