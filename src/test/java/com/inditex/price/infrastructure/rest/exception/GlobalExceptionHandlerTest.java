package com.inditex.price.infrastructure.rest.exception;

import com.inditex.price.domain.exception.*;
import com.inditex.price.model.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    @DisplayName("Should return 400 and generic message for invalid input or domain exceptions")
    void handleBadRequestDomainExceptions() {
        // Test all domain-related bad request exceptions
        Exception[] exceptions = new Exception[]{
                new InvalidDateRangeException("bad date"),
                new InvalidMoneyAmountException("bad money"),
                new DomainValidationException("invalid domain"),
                new MethodArgumentTypeMismatchException(null, null, "param", null, null)
        };

        for (Exception ex : exceptions) {
            ResponseEntity<ErrorResponse> response = handler.handleBadRequest(ex);
            assertThat(response.getStatusCode().value()).isEqualTo(400);
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody().getMessage())
                    .isEqualTo("Invalid request parameters");
        }
    }

    @Test
    @DisplayName("Should return 500 and internal error message for unexpected exceptions")
    void handleGeneric() {
        Exception ex = new RuntimeException("Unexpected failure");

        ResponseEntity<ErrorResponse> response = handler.handleGeneric(ex);

        assertThat(response.getStatusCode().value()).isEqualTo(500);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage())
                .isEqualTo("Internal server error");
    }
}
