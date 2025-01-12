package com.rustam.e_commerce.exception.custom;

import lombok.Getter;

@Getter
public class CartNotFoundException extends RuntimeException {

    private final String message;

    public CartNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
