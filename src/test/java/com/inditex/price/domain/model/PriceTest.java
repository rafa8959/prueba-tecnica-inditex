package com.inditex.price.domain.model;

import com.inditex.price.domain.exception.DomainValidationException;
import com.inditex.price.domain.model.vo.DateRange;
import com.inditex.price.domain.model.vo.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PriceTest {

    private final LocalDateTime START = LocalDateTime.of(2020, 6, 14, 0, 0);
    private final LocalDateTime END = LocalDateTime.of(2020, 6, 14, 18, 30);

    private final DateRange VALID_RANGE = DateRange.of(START, END);
    private final Money VALID_MONEY = Money.of(BigDecimal.valueOf(35.50), "EUR");


    @Test
    @DisplayName("Should create Price when all parameters are valid")
    void shouldCreateValidPrice() {
        Price price = Price.create()
                .brandId(1L)
                .productId(35455L)
                .priceList(1)
                .dateRange(VALID_RANGE)
                .priority(0)
                .money(VALID_MONEY)
                .build();

        assertNotNull(price);
        assertEquals(1L, price.getBrandId());
        assertEquals(35455L, price.getProductId());
        assertEquals(1, price.getPriceList());
        assertEquals(0, price.getPriority());
        assertEquals(VALID_MONEY, price.getMoney());
    }

    @Test
    @DisplayName("Should throw when brandId or productId is null")
    void shouldThrowWhenBrandOrProductIdIsNull() {
        DomainValidationException ex1 = assertThrows(
                DomainValidationException.class,
                () -> Price.create()
                        .brandId(null)
                        .productId(35455L)
                        .priceList(1)
                        .dateRange(VALID_RANGE)
                        .priority(0)
                        .money(VALID_MONEY)
                        .build()
        );
        assertEquals("BrandId and ProductId must not be null", ex1.getMessage());
    }

    @Test
    @DisplayName("Should throw when priceList is negative")
    void shouldThrowWhenPriceListNegative() {
        DomainValidationException ex = assertThrows(
                DomainValidationException.class,
                () -> Price.create()
                        .brandId(1L)
                        .productId(35455L)
                        .priceList(-1)
                        .dateRange(VALID_RANGE)
                        .priority(0)
                        .money(VALID_MONEY)
                        .build()
        );
        assertEquals("PriceList must be >= 0", ex.getMessage());
    }

    @Test
    @DisplayName("Should throw when priority is negative")
    void shouldThrowWhenPriorityNegative() {
        DomainValidationException ex = assertThrows(
                DomainValidationException.class,
                () -> Price.create()
                        .brandId(1L)
                        .productId(35455L)
                        .priceList(1)
                        .dateRange(VALID_RANGE)
                        .priority(-1)
                        .money(VALID_MONEY)
                        .build()
        );
        assertEquals("Priority must be >= 0", ex.getMessage());
    }

    @Test
    @DisplayName("Should throw when dateRange is null")
    void shouldThrowWhenDateRangeNull() {
        DomainValidationException ex = assertThrows(
                DomainValidationException.class,
                () -> Price.create()
                        .brandId(1L)
                        .productId(35455L)
                        .priceList(1)
                        .dateRange(null)
                        .priority(0)
                        .money(VALID_MONEY)
                        .build()
        );
        assertEquals("DateRange must not be null", ex.getMessage());
    }

    @Test
    @DisplayName("Should throw when money is null")
    void shouldThrowWhenMoneyNull() {
        DomainValidationException ex = assertThrows(
                DomainValidationException.class,
                () -> Price.create()
                        .brandId(1L)
                        .productId(35455L)
                        .priceList(1)
                        .dateRange(VALID_RANGE)
                        .priority(0)
                        .money(null)
                        .build()
        );
        assertEquals("Money must not be null", ex.getMessage());
    }


    @Test
    @DisplayName("Should return true when date is within range")
    void shouldReturnTrueIfDateWithinRange() {
        Price price = Price.create()
                .brandId(1L)
                .productId(35455L)
                .priceList(1)
                .dateRange(VALID_RANGE)
                .priority(0)
                .money(VALID_MONEY)
                .build();

        assertTrue(price.isApplicableAt(START.plusHours(10)));
    }

    @Test
    @DisplayName("Should return false when date is outside range")
    void shouldReturnFalseIfDateOutsideRange() {
        Price price = Price.create()
                .brandId(1L)
                .productId(35455L)
                .priceList(1)
                .dateRange(VALID_RANGE)
                .priority(0)
                .money(VALID_MONEY)
                .build();

        assertFalse(price.isApplicableAt(END.plusHours(5)));
    }

    @Test
    @DisplayName("Should determine higher priority correctly")
    void shouldComparePriorityCorrectly() {
        Price low = Price.create()
                .brandId(1L)
                .productId(35455L)
                .priceList(1)
                .dateRange(VALID_RANGE)
                .priority(0)
                .money(VALID_MONEY)
                .build();

        Price high = Price.create()
                .brandId(1L)
                .productId(35455L)
                .priceList(2)
                .dateRange(VALID_RANGE)
                .priority(1)
                .money(VALID_MONEY)
                .build();

        assertTrue(high.hasHigherPriorityThan(low));
        assertFalse(low.hasHigherPriorityThan(high));
    }

    @Test
    @DisplayName("Should sort prices by priority descending using comparator")
    void shouldSortByPriorityDescending() {
        Price p1 = Price.create()
                .brandId(1L).productId(1L)
                .priceList(1).dateRange(VALID_RANGE)
                .priority(0).money(VALID_MONEY).build();

        Price p2 = Price.create()
                .brandId(1L).productId(1L)
                .priceList(2).dateRange(VALID_RANGE)
                .priority(2).money(VALID_MONEY).build();

        Price p3 = Price.create()
                .brandId(1L).productId(1L)
                .priceList(3).dateRange(VALID_RANGE)
                .priority(1).money(VALID_MONEY).build();

        List<Price> prices = Arrays.asList(p1, p2, p3);
        prices.sort(Price.byPriorityDescending());

        assertEquals(p2, prices.get(0));
        assertEquals(p3, prices.get(1));
        assertEquals(p1, prices.get(2));
    }
}

