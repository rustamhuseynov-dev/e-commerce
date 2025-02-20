package com.rustam.e_commerce.dao.entity.user;

import com.rustam.e_commerce.dao.entity.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "shipment_tracking")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShipmentTracking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String trackingNumber;

    private String location;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Long orderId;

    private LocalDateTime updatedAt;

}
