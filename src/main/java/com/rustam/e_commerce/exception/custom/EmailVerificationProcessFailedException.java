package com.rustam.e_commerce.exception.custom;

import lombok.Getter;

@Getter
public class EmailVerificationProcessFailedException extends RuntimeException {
    private final String message;
    public EmailVerificationProcessFailedException(String message) {
        super(message);
        this.message = message;
    }
}
