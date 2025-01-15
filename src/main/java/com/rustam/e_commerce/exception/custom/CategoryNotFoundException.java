package com.rustam.e_commerce.exception.custom;

import lombok.Getter;

@Getter
public class CategoryNotFoundException extends RuntimeException {

    private final String message;

    public CategoryNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
