package com.rustam.e_commerce.exception.custom;

import lombok.Getter;

@Getter
public class ProductNotFoundException extends RuntimeException {

    private final String message;

    public ProductNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
