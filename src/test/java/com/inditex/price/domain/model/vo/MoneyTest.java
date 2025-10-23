package com.inditex.price.domain.model.vo;

import com.inditex.price.domain.exception.InvalidMoneyAmountException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    @Test
    @DisplayName("Should create Money with valid amount and currency")
    void shouldCreateMoneySuccessfully() {
        Money money = Money.of(BigDecimal.valueOf(10.50), "EUR");

        assertEquals(BigDecimal.valueOf(10.50), money.getAmount());
        assertEquals("EUR", money.getCurrency());
    }

    @Test
    @DisplayName("Should throw exception when amount is null")
    void shouldThrowWhenAmountIsNull() {
        InvalidMoneyAmountException exception = assertThrows(
                InvalidMoneyAmountException.class,
                () -> Money.of(null, "EUR")
        );

        assertEquals("Amount and currency must not be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when currency is null")
    void shouldThrowWhenCurrencyIsNull() {
        InvalidMoneyAmountException exception = assertThrows(
                InvalidMoneyAmountException.class,
                () -> Money.of(BigDecimal.ONE, null)
        );

        assertEquals("Amount and currency must not be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when amount is negative")
    void shouldThrowWhenAmountIsNegative() {
        InvalidMoneyAmountException exception = assertThrows(
                InvalidMoneyAmountException.class,
                () -> Money.of(BigDecimal.valueOf(-1), "EUR")
        );

        assertEquals("Amount must be greater than or equal to zero", exception.getMessage());
    }

    @Test
    @DisplayName("Should add two Money objects with the same currency")
    void shouldAddTwoMoneyObjectsWithSameCurrency() {
        Money m1 = Money.of(BigDecimal.valueOf(10.00), "EUR");
        Money m2 = Money.of(BigDecimal.valueOf(5.50), "EUR");

        Money result = m1.add(m2);

        assertEquals(Money.of(BigDecimal.valueOf(15.50), "EUR"), result);
    }

    @Test
    @DisplayName("Should throw exception when adding Money with different currencies")
    void shouldThrowWhenAddingDifferentCurrencies() {
        Money m1 = Money.of(BigDecimal.valueOf(10.00), "EUR");
        Money m2 = Money.of(BigDecimal.valueOf(5.00), "USD");

        InvalidMoneyAmountException exception = assertThrows(
                InvalidMoneyAmountException.class,
                () -> m1.add(m2)
        );

        assertEquals("Cannot add amounts with different currencies", exception.getMessage());
    }

    @Test
    @DisplayName("Should compare equal Money objects correctly")
    void shouldCompareEqualMoneyObjects() {
        Money m1 = Money.of(BigDecimal.valueOf(10.0), "EUR");
        Money m2 = Money.of(BigDecimal.valueOf(10.00), "EUR");

        assertEquals(m1, m2);
        assertEquals(m1.hashCode(), m2.hashCode());
    }

    @Test
    @DisplayName("Should print Money in human-readable format")
    void shouldPrintToStringProperly() {
        Money money = Money.of(BigDecimal.valueOf(25.45), "EUR");
        assertEquals("25.45 EUR", money.toString());
    }
}

