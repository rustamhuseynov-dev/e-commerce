package com.rustam.e_commerce.dao.repository;

import com.rustam.e_commerce.dao.entity.user.ShipmentTracking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShipmentTrackingRepository extends JpaRepository<ShipmentTracking,Long> {
    Optional<ShipmentTracking> findByTrackingNumber(String trackingNumber);
}
