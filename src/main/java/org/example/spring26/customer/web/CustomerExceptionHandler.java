package org.example.spring26.customer.web;

import org.example.spring26.customer.CustomerModule;
import org.example.spring26.customer.application.CustomerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackageClasses = CustomerModule.class)
public class CustomerExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<?> handle(CustomerNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid Customer ID");
    }
}
