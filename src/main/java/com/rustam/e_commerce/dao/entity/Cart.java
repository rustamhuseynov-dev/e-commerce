package com.rustam.e_commerce.dao.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.rustam.e_commerce.dao.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

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

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @ToString.Exclude // Exclude cartItems to avoid recursion
    private List<CartItem> cartItems = new ArrayList<>();

    private double totalPrice;
}
