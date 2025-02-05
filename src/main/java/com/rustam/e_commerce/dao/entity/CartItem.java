package com.rustam.e_commerce.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

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
    @JsonIgnore
    @ToString.Exclude // Exclude cart to avoid recursion
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIgnore
    @ToString.Exclude // Sonsuz rekursiyanın qarşısını alır
    private Product product;

    private String productName;
    private Integer quantity;
    private double price;
    private double totalPrice;
    public void calculateTotalPrice() {
        this.totalPrice = this.quantity * this.price;
    }
}
