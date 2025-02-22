package com.rustam.e_commerce.dto.response;

import com.rustam.e_commerce.dao.entity.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrackOrderResponse {
    private String trackingNumber;

    private String location;

    private OrderStatus status;

    private Long orderId;

    private LocalDateTime updatedAt;
}
