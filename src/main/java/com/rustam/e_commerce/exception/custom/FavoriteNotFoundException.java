package com.rustam.e_commerce.exception.custom;

import lombok.Getter;

@Getter
public class FavoriteNotFoundException extends RuntimeException {
    private final String message;
    public FavoriteNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
