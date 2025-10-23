package com.inditex.price.domain.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DomainExceptionTest {

    static class TestDomainException extends DomainException {
		private static final long serialVersionUID = -3278627278109330916L;

		public TestDomainException(String message) {
            super(message);
        }
    }

    @Test
    @DisplayName("Should store the message correctly")
    void shouldStoreMessageCorrectly() {
        String message = "Domain validation failed";
        DomainException exception = new TestDomainException(message);

        assertEquals(message, exception.getMessage());
    }

    @Test
    @DisplayName("Should be instance of RuntimeException")
    void shouldBeInstanceOfRuntimeException() {
        DomainException exception = new TestDomainException("Error");
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    @DisplayName("Should be catchable as DomainException")
    void shouldBeCatchableAsDomainException() {
        assertThrows(DomainException.class, () -> {
            throw new TestDomainException("Domain error occurred");
        });
    }
}
