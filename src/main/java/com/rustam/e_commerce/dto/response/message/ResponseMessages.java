package com.rustam.e_commerce.dto.response.message;

import org.springframework.http.HttpStatus;

public interface ResponseMessages {

    String key();
    String message();
    HttpStatus status();
}
