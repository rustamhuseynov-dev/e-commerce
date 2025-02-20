package com.rustam.e_commerce.controller;

import com.rustam.e_commerce.dao.entity.user.ShipmentTracking;
import com.rustam.e_commerce.dto.request.TrackOrderRequest;
import com.rustam.e_commerce.dto.response.TrackOrderResponse;
import com.rustam.e_commerce.service.ShipmentTrackingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/shipment-tracking")
@RequiredArgsConstructor
public class ShipmentTrackingController {

    private final ShipmentTrackingService shipmentTrackingService;

    @GetMapping(path = "/track-order")
    public ResponseEntity<TrackOrderResponse> trackOrder(@RequestBody TrackOrderRequest trackOrderRequest){
        return new ResponseEntity<>(shipmentTrackingService.trackOrder(trackOrderRequest), HttpStatus.ACCEPTED);
    }
}
