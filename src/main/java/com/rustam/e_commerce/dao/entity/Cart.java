package com.rustam.e_commerce.dao.entity;

import com.rustam.e_commerce.dao.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "carts")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private UUID user;

    @OneToMany(mappedBy = "cart", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();

    private double totalPrice;

    public void calculateTotalPrice() {
        this.totalPrice = cartItems.stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum();
    }
}
