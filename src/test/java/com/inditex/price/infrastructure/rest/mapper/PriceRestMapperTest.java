package com.inditex.price.infrastructure.rest.mapper;

import com.inditex.price.domain.model.Price;
import com.inditex.price.domain.model.vo.DateRange;
import com.inditex.price.domain.model.vo.Money;
import com.inditex.price.model.PriceResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class PriceRestMapperTest {

    private Price createPrice(int priceList, int priority, double amount) {
        return Price.builder()
                .brandId(1L)
                .productId(35455L)
                .priceList(priceList)
                .dateRange(DateRange.of(
                        LocalDateTime.of(2020, 6, 14, 0, 0),
                        LocalDateTime.of(2020, 6, 14, 18, 30)))
                .priority(priority)
                .money(Money.of(BigDecimal.valueOf(amount), "EUR"))
                .build();
    }

    @Test
    @DisplayName("Should map a valid Price domain object to PriceResponse correctly")
    void shouldMapPriceToResponse() {
        Price price = createPrice(2, 1, 25.45);

        PriceResponse response = PriceRestMapper.toResponse(price, true);

        assertThat(response.getProductId()).isEqualTo(35455);
        assertThat(response.getBrandId()).isEqualTo(1);
        assertThat(response.getPriceList()).isEqualTo(2);
        assertThat(response.getPriority()).isEqualTo(1);
        assertThat(response.getCurrency()).isEqualTo("EUR");
        assertThat(response.getPrice()).isEqualTo(25.45);
        assertThat(response.getApplied()).isTrue();
        assertThat(response.getStartDate()).isNotNull();
        assertThat(response.getEndDate()).isNotNull();
    }

    @Test
    @DisplayName("Should map list of prices and mark first as applied")
    void shouldMapListAndMarkFirstAsApplied() {
        List<Price> prices = List.of(
                createPrice(1, 0, 35.50),
                createPrice(2, 1, 25.45)
        );

        List<PriceResponse> responses = PriceRestMapper.toResponseList(prices);

        assertThat(responses).hasSize(2);
        assertThat(responses.get(0).getApplied()).isTrue();
        assertThat(responses.get(1).getApplied()).isFalse();
    }

    @Test
    @DisplayName("Should return empty list when input is null or empty")
    void shouldReturnEmptyListForNullOrEmpty() {
        assertThat(PriceRestMapper.toResponseList(null)).isEmpty();
        assertThat(PriceRestMapper.toResponseList(List.of())).isEmpty();
    }

    @Test
    @DisplayName("Should throw NullPointerException when Price is null")
    void shouldThrowWhenPriceIsNull() {
        assertThatThrownBy(() -> PriceRestMapper.toResponse(null, false))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Should throw when Price has missing attributes (simulated with mock)")
    void shouldThrowWhenPriceHasMissingAttributes() {
        Price invalid = mock(Price.class);
        when(invalid.getProductId()).thenReturn(35455L);
        when(invalid.getBrandId()).thenReturn(1L);
        when(invalid.getPriceList()).thenReturn(1);
        when(invalid.getDateRange()).thenReturn(null); // invalid
        when(invalid.getPriority()).thenReturn(0);
        when(invalid.getMoney()).thenReturn(null); // invalid

        assertThatThrownBy(() -> PriceRestMapper.toResponse(invalid, false))
                .isInstanceOf(NullPointerException.class);
    }
}
