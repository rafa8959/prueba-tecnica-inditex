package com.inditex.price.domain.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvalidMoneyAmountExceptionTest {

    @Test
    @DisplayName("Should store the provided message")
    void shouldStoreMessage() {
        String message = "Amount must be greater than or equal to zero";
        InvalidMoneyAmountException exception = new InvalidMoneyAmountException(message);

        assertEquals(message, exception.getMessage());
    }

    @Test
    @DisplayName("Should be instance of DomainException")
    void shouldBeInstanceOfDomainException() {
        InvalidMoneyAmountException exception = new InvalidMoneyAmountException("Invalid amount");
        assertTrue(exception instanceof DomainException);
    }

    @Test
    @DisplayName("Should be instance of RuntimeException")
    void shouldBeInstanceOfRuntimeException() {
        InvalidMoneyAmountException exception = new InvalidMoneyAmountException("Invalid amount");
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    @DisplayName("Should be thrown and caught as InvalidMoneyAmountException")
    void shouldBeThrownAndCaught() {
        assertThrows(InvalidMoneyAmountException.class, () -> {
            throw new InvalidMoneyAmountException("Negative amount detected");
        });
    }
}
