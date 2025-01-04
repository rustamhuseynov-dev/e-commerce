package com.rustam.e_commerce.dto.response.message;

import org.springframework.http.HttpStatus;

public record ExceptionResponseMessages
        (String key,
         String message,
         HttpStatus status) implements ResponseMessages {

}
