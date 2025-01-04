package com.rustam.e_commerce.exception.custom;

import lombok.Getter;

@Getter
public class InvalidUUIDFormatException extends RuntimeException {

    private final String message;

    public InvalidUUIDFormatException(String s, IllegalArgumentException e) {
        super(s,e);
        this.message = s;
    }
}
