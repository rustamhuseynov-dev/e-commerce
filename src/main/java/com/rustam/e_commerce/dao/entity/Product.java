package com.rustam.e_commerce.dao.entity;

import com.rustam.e_commerce.dao.entity.campaign.Campaign;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "campaign_id")
    private Campaign campaign;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @ToString.Exclude // Sonsuz rekursiyanın qarşısını alır
    private List<CartItem> products = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @ToString.Exclude // Sonsuz rekursiyanın qarşısını alır
    private List<OrderItem> orderItems = new ArrayList<>();
}