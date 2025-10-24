package com.inditex.price.domain.service;

import com.inditex.price.domain.exception.InvalidPriceListException;
import com.inditex.price.domain.model.Price;
import com.inditex.price.domain.model.vo.DateRange;
import com.inditex.price.domain.model.vo.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class PriceDomainServiceTest {

    private final PriceDomainService service = new PriceDomainService();

    private final LocalDateTime START = LocalDateTime.of(2020, 6, 14, 0, 0);
    private final LocalDateTime END = LocalDateTime.of(2020, 6, 14, 18, 30);

    private Price createPrice(int priceList, int priority, double amount) {
        return Price.create()
                .brandId(1L)
                .productId(35455L)
                .priceList(priceList)
                .dateRange(DateRange.of(START, END))
                .priority(priority)
                .money(Money.of(BigDecimal.valueOf(amount), "EUR"))
                .build();
    }

    @Test
    @DisplayName("Should return list of prices sorted by priority descending")
    void shouldReturnPricesSortedByPriority() {
        Price low = createPrice(1, 0, 35.50);
        Price high = createPrice(2, 1, 25.45);

        List<Price> result = service.sortApplicablePricesByPriority(Arrays.asList(low, high));

        assertThat(result).hasSize(2);
        assertThat(result.get(0)).isEqualTo(high);
        assertThat(result.get(1)).isEqualTo(low);
    }

    @Test
    @DisplayName("Should throw InvalidPriceListException when list is null or empty")
    void shouldThrowWhenPriceListIsNullOrEmpty() {
        assertThatThrownBy(() -> service.sortApplicablePricesByPriority(null))
                .isInstanceOf(InvalidPriceListException.class)
                .hasMessageContaining("Price list must not be null");

        assertThatThrownBy(() -> service.sortApplicablePricesByPriority(Collections.emptyList()))
                .isInstanceOf(InvalidPriceListException.class)
                .hasMessageContaining("Price list must not be null");
    }
}
