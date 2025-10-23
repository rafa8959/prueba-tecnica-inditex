package com.inditex.price.domain.service;

import com.inditex.price.domain.exception.PriceNotFoundException;
import com.inditex.price.domain.model.Price;
import com.inditex.price.domain.model.vo.DateRange;
import com.inditex.price.domain.model.vo.Money;
import com.inditex.price.domain.repository.PriceRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PriceDomainServiceTest {

    private final PriceRepository repository = Mockito.mock(PriceRepository.class);
    private final PriceDomainService service = new PriceDomainService(repository);

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

        when(repository.findApplicablePrices(1L, 35455L, START.plusHours(10)))
                .thenReturn(Arrays.asList(low, high));

        List<Price> result = service.execute(1L, 35455L, START.plusHours(10));

        assertEquals(2, result.size());
        assertEquals(high, result.get(0)); 
        assertEquals(low, result.get(1));

        verify(repository).findApplicablePrices(1L, 35455L, START.plusHours(10));
    }

    @Test
    @DisplayName("Should throw PriceNotFoundException when no prices are found")
    void shouldThrowWhenNoPricesFound() {
        when(repository.findApplicablePrices(1L, 35455L, START.plusHours(10)))
                .thenReturn(Collections.emptyList());

        assertThrows(
                PriceNotFoundException.class,
                () -> service.execute(1L, 35455L, START.plusHours(10))
        );
    }
}
