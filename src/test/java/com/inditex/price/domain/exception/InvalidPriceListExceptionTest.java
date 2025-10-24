package com.inditex.price.domain.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InvalidPriceListExceptionTest {

    @Test
    @DisplayName("Should create exception with given message and extend DomainException")
    void shouldCreateExceptionWithMessage() {
        // Given
        String message = "Price list must not be null";

        // When
        InvalidPriceListException exception = new InvalidPriceListException(message);

        // Then
        assertThat(exception)
                .isInstanceOf(DomainException.class)
                .hasMessage(message);
    }
}
