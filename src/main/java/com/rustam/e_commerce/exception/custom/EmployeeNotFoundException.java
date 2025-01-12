package com.rustam.e_commerce.exception.custom;

import lombok.Getter;

@Getter
public class EmployeeNotFoundException extends RuntimeException {

    private final String message;

    public EmployeeNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
