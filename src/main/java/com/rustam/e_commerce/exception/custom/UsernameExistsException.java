package com.rustam.e_commerce.exception.custom;

import lombok.Getter;

@Getter
public class UsernameExistsException extends RuntimeException {
    private final String message;
    public UsernameExistsException(String message) {
        super(message);
        this.message = message;
    }
}
