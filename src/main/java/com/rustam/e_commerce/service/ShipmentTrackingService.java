package com.rustam.e_commerce.service;

import com.rustam.e_commerce.dao.entity.user.ShipmentTracking;
import com.rustam.e_commerce.dao.repository.ShipmentTrackingRepository;
import com.rustam.e_commerce.dto.request.TrackOrderRequest;
import com.rustam.e_commerce.dto.response.TrackOrderResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class ShipmentTrackingService {

    ShipmentTrackingRepository shipmentTrackingRepository;

    public TrackOrderResponse trackOrder(TrackOrderRequest trackOrderRequest) {
    }

    public void save(ShipmentTracking shipmentTracking) {
        shipmentTrackingRepository.save(shipmentTracking);
    }
}
