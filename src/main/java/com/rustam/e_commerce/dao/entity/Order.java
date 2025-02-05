package com.rustam.e_commerce.dao.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.rustam.e_commerce.dao.entity.enums.OrderStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.*;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(nullable = false)
    private String userId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @ToString.Exclude // Lombok-un sonsuz rekursiyanın qarşısını alır
    @JsonIgnore // JSON serialization zamanı nəzərə almır
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDate orderDate;

    @OneToOne
    @JoinColumn(name = "payment_id")
    @ToString.Exclude // Lombok-un sonsuz rekursiyanın qarşısını alır
    @JsonIgnore // JSON serialization zamanı nəzərə almır
    private Payment payment;

    private Double totalAmount;
    private OrderStatus orderStatus;
}
