package com.rustam.e_commerce.dao.repository;

import com.rustam.e_commerce.dao.entity.user.ShipmentTracking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentTrackingRepository extends JpaRepository<ShipmentTracking,Long> {
}
