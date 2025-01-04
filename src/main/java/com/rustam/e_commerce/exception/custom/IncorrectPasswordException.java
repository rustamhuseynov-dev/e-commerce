package com.rustam.e_commerce.exception.custom;

import lombok.Getter;

@Getter
public class IncorrectPasswordException extends RuntimeException {

    private final String message;

    public IncorrectPasswordException(String message){
        super(message);
        this.message = message;
    }
}
