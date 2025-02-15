package com.rustam.e_commerce.exception.custom;

import lombok.Getter;

@Getter
public class EmailExistsException extends RuntimeException {
    private final String message;

    public EmailExistsException(String message) {
        super(message);
        this.message = message;
    }
}
