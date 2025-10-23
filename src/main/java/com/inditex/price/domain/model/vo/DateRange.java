package com.inditex.price.domain.model.vo;

import java.time.LocalDateTime;
import java.util.Objects;

import com.inditex.price.domain.exception.InvalidDateRangeException;

public class DateRange {
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    private DateRange(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null || endDate == null)
            throw new InvalidDateRangeException("Start and end dates must not be null");
        if (endDate.isBefore(startDate))
            throw new InvalidDateRangeException("End date must be after start date");
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
    public static DateRange of(LocalDateTime startDate, LocalDateTime endDate) {
        return new DateRange(startDate, endDate);
    }

    public boolean includes(LocalDateTime date) {
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }

    public LocalDateTime getStartDate() { return startDate; }
    public LocalDateTime getEndDate() { return endDate; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DateRange)) return false;
        DateRange that = (DateRange) o;
        return Objects.equals(startDate, that.startDate) &&
               Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate);
    }

    @Override
    public String toString() {
        return "[" + startDate + " - " + endDate + "]";
    }
}
