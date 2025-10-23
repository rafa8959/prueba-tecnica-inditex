package com.inditex.price.domain.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvalidDateRangeExceptionTest {

    @Test
    @DisplayName("Should store the provided message")
    void shouldStoreMessage() {
        String message = "End date must be after start date";
        InvalidDateRangeException exception = new InvalidDateRangeException(message);

        assertEquals(message, exception.getMessage());
    }

    @Test
    @DisplayName("Should be instance of DomainException")
    void shouldBeInstanceOfDomainException() {
        InvalidDateRangeException exception = new InvalidDateRangeException("Invalid range");
        assertTrue(exception instanceof DomainException);
    }

    @Test
    @DisplayName("Should be instance of RuntimeException")
    void shouldBeInstanceOfRuntimeException() {
        InvalidDateRangeException exception = new InvalidDateRangeException("Invalid range");
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    @DisplayName("Should be thrown and caught as InvalidDateRangeException")
    void shouldBeThrownAndCaught() {
        assertThrows(InvalidDateRangeException.class, () -> {
            throw new InvalidDateRangeException("Invalid date range detected");
        });
    }
}
