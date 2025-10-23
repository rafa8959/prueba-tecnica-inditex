package com.inditex.price.domain.model.vo;

import com.inditex.price.domain.exception.InvalidDateRangeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DateRangeTest {

    private final LocalDateTime START = LocalDateTime.of(2020, 6, 14, 0, 0);
    private final LocalDateTime END = LocalDateTime.of(2020, 6, 14, 18, 30);

    @Test
    @DisplayName("Should create a valid DateRange when start <= end")
    void shouldCreateValidDateRange() {
        DateRange range = new DateRange(START, END);

        assertEquals(START, range.getStartDate());
        assertEquals(END, range.getEndDate());
    }

    @Test
    @DisplayName("Should throw when startDate is null")
    void shouldThrowWhenStartDateIsNull() {
        InvalidDateRangeException ex = assertThrows(
                InvalidDateRangeException.class,
                () -> new DateRange(null, END)
        );

        assertEquals("Start and end dates must not be null", ex.getMessage());
    }

    @Test
    @DisplayName("Should throw when endDate is null")
    void shouldThrowWhenEndDateIsNull() {
        InvalidDateRangeException ex = assertThrows(
                InvalidDateRangeException.class,
                () -> new DateRange(START, null)
        );

        assertEquals("Start and end dates must not be null", ex.getMessage());
    }

    @Test
    @DisplayName("Should throw when endDate is before startDate")
    void shouldThrowWhenEndBeforeStart() {
        LocalDateTime before = LocalDateTime.of(2020, 6, 13, 23, 59);

        InvalidDateRangeException ex = assertThrows(
                InvalidDateRangeException.class,
                () -> new DateRange(START, before)
        );

        assertEquals("End date must be after start date", ex.getMessage());
    }

    @Test
    @DisplayName("Should include dates exactly at the start and end boundaries")
    void shouldIncludeBoundaryDates() {
        DateRange range = new DateRange(START, END);

        assertTrue(range.includes(START));
        assertTrue(range.includes(END));
    }

    @Test
    @DisplayName("Should include dates between start and end")
    void shouldIncludeDatesWithinRange() {
        DateRange range = new DateRange(START, END);

        LocalDateTime mid = LocalDateTime.of(2020, 6, 14, 10, 0);
        assertTrue(range.includes(mid));
    }

    @Test
    @DisplayName("Should not include dates before start or after end")
    void shouldNotIncludeDatesOutsideRange() {
        DateRange range = new DateRange(START, END);

        LocalDateTime before = LocalDateTime.of(2020, 6, 13, 23, 59);
        LocalDateTime after = LocalDateTime.of(2020, 6, 14, 19, 0);

        assertFalse(range.includes(before));
        assertFalse(range.includes(after));
    }

    @Test
    @DisplayName("Should compare equal DateRange objects correctly")
    void shouldCompareEqualDateRanges() {
        DateRange r1 = new DateRange(START, END);
        DateRange r2 = new DateRange(START, END);

        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    @DisplayName("Should print DateRange in human-readable format")
    void shouldPrintToStringProperly() {
        DateRange range = new DateRange(START, END);

        assertEquals("[" + START + " - " + END + "]", range.toString());
    }
}

