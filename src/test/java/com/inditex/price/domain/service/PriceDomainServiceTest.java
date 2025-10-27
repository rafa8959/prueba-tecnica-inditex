package com.inditex.price.domain.service;

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
    @DisplayName("Should return the product price with the highest priority")
    void shouldReturnPricesSortedByPriority() {
        Price low = createPrice(1, 0, 35.50);
        Price high = createPrice(2, 1, 25.45);

        List<Price> result = service.filterHighestPriorityPrices(Arrays.asList(low, high));

        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(high);
    }

    @Test
    @DisplayName("Should return empty list when list is null or empty")
    void shouldThrowWhenPriceListIsNullOrEmpty() {
    	List<Price> result =  service.filterHighestPriorityPrices(null);
        assertThat(result).hasSize(0);

        result = service.filterHighestPriorityPrices(Collections.emptyList());
        assertThat(result).hasSize(0);
    }
}
