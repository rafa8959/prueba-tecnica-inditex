package com.inditex.price.domain.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DomainValidationExceptionTest {

    @Test
    @DisplayName("Should store the provided message")
    void shouldStoreMessage() {
        String message = "Validation error: invalid field";
        DomainValidationException exception = new DomainValidationException(message);

        assertEquals(message, exception.getMessage());
    }

    @Test
    @DisplayName("Should be instance of DomainException")
    void shouldBeInstanceOfDomainException() {
        DomainValidationException exception = new DomainValidationException("Error");
        assertTrue(exception instanceof DomainException);
    }

    @Test
    @DisplayName("Should be instance of RuntimeException")
    void shouldBeInstanceOfRuntimeException() {
        DomainValidationException exception = new DomainValidationException("Error");
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    @DisplayName("Should be thrown and caught as DomainValidationException")
    void shouldBeThrownAndCaught() {
        assertThrows(DomainValidationException.class, () -> {
            throw new DomainValidationException("Invalid price data");
        });
    }
}
