package com.rustam.e_commerce.exception.custom;

import lombok.Getter;

@Getter
public class ShipmentTrackingNotFoundException extends RuntimeException {

    private final String message;
    public ShipmentTrackingNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
