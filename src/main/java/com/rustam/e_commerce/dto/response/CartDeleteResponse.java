package com.rustam.e_commerce.dto.response;

import com.rustam.e_commerce.dao.entity.CartItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDeleteResponse {
    private Long id;
    private Long userId;
    private List<CartItem> items;
    private double totalPrice;
    private String message;
}
