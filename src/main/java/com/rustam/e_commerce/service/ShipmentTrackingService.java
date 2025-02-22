package com.rustam.e_commerce.service;

import com.rustam.e_commerce.dao.entity.user.ShipmentTracking;
import com.rustam.e_commerce.dao.repository.ShipmentTrackingRepository;
import com.rustam.e_commerce.dto.request.TrackOrderRequest;
import com.rustam.e_commerce.dto.response.TrackOrderResponse;
import com.rustam.e_commerce.exception.custom.ShipmentTrackingNotFoundException;
import com.rustam.e_commerce.mapper.ShipmentTrackingMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class ShipmentTrackingService {

    ShipmentTrackingRepository shipmentTrackingRepository;
    ShipmentTrackingMapper shipmentTrackingMapper;

    public TrackOrderResponse trackOrder(TrackOrderRequest trackOrderRequest) {
        ShipmentTracking byTrackingNumber = shipmentTrackingRepository.findByTrackingNumber(trackOrderRequest.getTrackingNumber())
                .orElseThrow(() -> new ShipmentTrackingNotFoundException("No tracking for such a shipment was found."));
        return shipmentTrackingMapper.toDto(byTrackingNumber);
    }

    public void save(ShipmentTracking shipmentTracking) {
        shipmentTrackingRepository.save(shipmentTracking);
    }
}
