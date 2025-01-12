package com.rustam.e_commerce.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cart_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @JoinColumn(name = "product_id")
    private Long product;

    private String productName;
    private Integer quantity;
    private double price;
    private double totalPrice;

    public void calculateTotalPrice() {
        this.totalPrice = this.quantity * this.price;
    }
}
