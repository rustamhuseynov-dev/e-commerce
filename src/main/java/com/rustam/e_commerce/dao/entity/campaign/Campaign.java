package com.rustam.e_commerce.dao.entity.campaign;

import com.rustam.e_commerce.dao.entity.Product;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "campaign")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "discount_percentage")
    private Long discountPercentage;
    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL)
    @ToString.Exclude
    @Builder.Default
    private List<Product> products = new ArrayList<>();
}
