package com.rustam.e_commerce.dao.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String productName;

    private String description;

    private Integer quantity;
    private double price;
    private double discount;
    private double specialPrice;
    private String userId;
    private String imageUrl;
    private String videoUrl;

    @JoinColumn(name = "category_id")
    private Long categoryId;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @ToString.Exclude // Sonsuz rekursiyanın qarşısını alır
    private List<CartItem> products = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @ToString.Exclude // Sonsuz rekursiyanın qarşısını alır
    private List<OrderItem> orderItems = new ArrayList<>();
}