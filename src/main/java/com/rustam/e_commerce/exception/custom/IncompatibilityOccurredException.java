package com.rustam.e_commerce.exception.custom;

import lombok.Getter;

@Getter
public class IncompatibilityOccurredException extends RuntimeException {

    private final String message;
    public IncompatibilityOccurredException(String message) {
        super(message);
        this.message = message;
    }
}
