package com.inditex.price.infrastructure.rest.exception;

import com.inditex.price.domain.exception.*;
import com.inditex.price.model.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler({
        InvalidDateRangeException.class,
        InvalidMoneyAmountException.class,
        DomainValidationException.class,
        MethodArgumentTypeMismatchException.class,
        MethodArgumentNotValidException.class
    })
    public ResponseEntity<ErrorResponse> handleBadRequest(Exception ex) {
    	log.error("Error", ex);
        ErrorResponse error = new ErrorResponse("Invalid request parameters");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
    
    
    @ExceptionHandler({PriceNotFoundException.class, 
    	InvalidPriceListException.class})
    public ResponseEntity<ErrorResponse> handlePriceNotFound(RuntimeException ex) {
    	log.warn("Price not found: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse("No price found for given parameters");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
    	log.error("Unexpected error", ex);
        ErrorResponse error = new ErrorResponse("Internal server error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
