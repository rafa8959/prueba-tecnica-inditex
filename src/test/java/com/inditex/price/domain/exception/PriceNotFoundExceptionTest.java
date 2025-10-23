package com.inditex.price.domain.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PriceNotFoundExceptionTest {

    @Test
    @DisplayName("Should store brandId, productId, and date correctly")
    void shouldStoreAttributes() {
        Long brandId = 1L;
        Long productId = 35455L;
        String date = "2020-06-14T16:00:00";

        PriceNotFoundException exception = new PriceNotFoundException(brandId, productId, date);

        assertEquals(brandId, exception.getBrandId());
        assertEquals(productId, exception.getProductId());
        assertEquals(date, exception.getDate());
    }

    @Test
    @DisplayName("Should format the message correctly")
    void shouldFormatMessageCorrectly() {
        PriceNotFoundException exception = new PriceNotFoundException(1L, 35455L, "2020-06-14T16:00:00");

        String expectedMessage = "No price found for brand 1, product 35455, at 2020-06-14T16:00:00";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    @DisplayName("Should be instance of DomainException and RuntimeException")
    void shouldBeInstanceOfDomainAndRuntimeException() {
        PriceNotFoundException exception = new PriceNotFoundException(1L, 35455L, "2020-06-14T16:00:00");

        assertTrue(exception instanceof DomainException);
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    @DisplayName("Should be thrown and caught as PriceNotFoundException")
    void shouldBeThrownAndCaught() {
        assertThrows(PriceNotFoundException.class, () -> {
            throw new PriceNotFoundException(1L, 35455L, "2020-06-14T16:00:00");
        });
    }
}

