package com.inditex.price.domain.exception;

public class InvalidPriceListException extends DomainException {

    private static final long serialVersionUID = 1L;

    public InvalidPriceListException(String message) {
        super(message);
    }
}
