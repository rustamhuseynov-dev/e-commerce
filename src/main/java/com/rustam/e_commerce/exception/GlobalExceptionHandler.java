package com.rustam.e_commerce.exception;

import com.rustam.e_commerce.dto.response.message.ExceptionResponseMessages;
import com.rustam.e_commerce.exception.custom.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionResponseMessages> handleUserNotFoundException(UsernameNotFoundException ex) {
        return new ResponseEntity<>(
                new ExceptionResponseMessages(ex.getClass().getName(), ex.getMessage(), HttpStatus.NOT_FOUND) ,
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<ExceptionResponseMessages> handleIncorrectPasswordException(IncorrectPasswordException ex) {
        return new ResponseEntity<>(
                new ExceptionResponseMessages(ex.getClass().getName(), ex.getMessage(), HttpStatus.CONFLICT),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionResponseMessages> handleIncorrectPasswordException(UserNotFoundException ex) {
        return new ResponseEntity<>(
                new ExceptionResponseMessages(ex.getClass().getName(), ex.getMessage(), HttpStatus.CONFLICT),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(IncompatibilityOccurredException.class)
    public ResponseEntity<ExceptionResponseMessages> handleIncorrectPasswordException(IncompatibilityOccurredException ex) {
        return new ResponseEntity<>(
                new ExceptionResponseMessages(ex.getClass().getName(), ex.getMessage(), HttpStatus.CONFLICT),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(EmailVerificationProcessFailedException.class)
    public ResponseEntity<ExceptionResponseMessages> handleIncorrectPasswordException(EmailVerificationProcessFailedException ex) {
        return new ResponseEntity<>(
                new ExceptionResponseMessages(ex.getClass().getName(), ex.getMessage(), HttpStatus.CONFLICT),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(NotManyProductsException.class)
    public ResponseEntity<ExceptionResponseMessages> handleIncorrectPasswordException(NotManyProductsException ex) {
        return new ResponseEntity<>(
                new ExceptionResponseMessages(ex.getClass().getName(), ex.getMessage(), HttpStatus.CONFLICT),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(NoAuthotiryException.class)
    public ResponseEntity<ExceptionResponseMessages> handleIncorrectPasswordException(NoAuthotiryException ex) {
        return new ResponseEntity<>(
                new ExceptionResponseMessages(ex.getClass().getName(), ex.getMessage(), HttpStatus.CONFLICT),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ExceptionResponseMessages> handleUserNotFoundException(ProductNotFoundException ex) {
        return new ResponseEntity<>(
                new ExceptionResponseMessages(ex.getClass().getName(), ex.getMessage(), HttpStatus.NOT_FOUND) ,
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ExceptionResponseMessages> handleUserNotFoundException(CartNotFoundException ex) {
        return new ResponseEntity<>(
                new ExceptionResponseMessages(ex.getClass().getName(), ex.getMessage(), HttpStatus.NOT_FOUND) ,
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ExceptionResponseMessages> handleUserNotFoundException(CategoryNotFoundException ex) {
        return new ResponseEntity<>(
                new ExceptionResponseMessages(ex.getClass().getName(), ex.getMessage(), HttpStatus.NOT_FOUND) ,
                HttpStatus.NOT_FOUND
        );
    }



    // Validation exceptions
    // Learn:
    //  MethodArgumentNotValidException is specific to Spring and is thrown when there are validation errors during the binding of method parameters, typically in a Spring MVC controller method.
    //  It is commonly used when validating incoming request data, such as form submissions or JSON payloads,
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.UNPROCESSABLE_ENTITY);
    }
    // Learn:
    //  It is typically thrown when there are constraint violations during the validation of entities or objects, usually when using annotations like @NotNull, @Size, @Email, etc., on fields or properties of a Java class.
    //  This exception can occur when validating entities outside the context of Spring MVC, for example, in a JPA (Java Persistence API) environment.
    //  If you try to persist a User object with a null username using JPA, a ConstraintViolationException may be thrown
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        Map<String, String> errors = new HashMap<>();
        for (ConstraintViolation<?> violation : violations) {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            errors.put(fieldName, errorMessage);
        }
        return new ResponseEntity<>(errors, HttpStatus.UNPROCESSABLE_ENTITY);
    }
    // For unhandled exceptions:
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ExceptionResponseMessages> generalExceptionHandler(Exception ex){
        System.out.println("For unhandled exceptions");
        System.out.println(ex.getClass());
        return new ResponseEntity<>(
                new ExceptionResponseMessages(ex.getClass().getName(), ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR) ,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
