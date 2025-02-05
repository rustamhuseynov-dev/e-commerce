package com.rustam.e_commerce.exception.custom;

import lombok.Getter;

@Getter
public class NotManyProductsException extends RuntimeException {
    private final String message;
    public NotManyProductsException(String message) {
        super(message);
        this.message = message;
    }
}
