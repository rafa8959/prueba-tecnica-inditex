package com.inditex.price.infrastructure.rest.controller;

import com.inditex.price.application.usecase.GetApplicablePricesUseCase;
import com.inditex.price.domain.model.Price;
import com.inditex.price.domain.model.vo.DateRange;
import com.inditex.price.domain.model.vo.Money;
import com.inditex.price.model.PriceResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.*;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class PriceControllerTest {

    private GetApplicablePricesUseCase useCase;
    private PriceController controller;

    @BeforeEach
    void setUp() {
        useCase = mock(GetApplicablePricesUseCase.class);
        controller = new PriceController(useCase);
    }

    private Price createDomainPrice(int priceList, int priority, double amount) {
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
    @DisplayName("Should return list of mapped PriceResponse when use case returns domain prices")
    void shouldReturnMappedPrices() {
        // Given
        OffsetDateTime requestDate = OffsetDateTime.of(2020, 6, 14, 10, 0, 0, 0, ZoneOffset.UTC);
        Long brandId = 1L;
        Long productId = 35455L;

        List<Price> domainPrices = List.of(createDomainPrice(1, 0, 35.50), createDomainPrice(2, 1, 25.45));

        when(useCase.getApplicablePricesSortedByPriority(brandId, productId, requestDate.toLocalDateTime()))
                .thenReturn(domainPrices);

        ResponseEntity<List<PriceResponse>> response = controller.getApplicablePrices(requestDate, productId.intValue(), brandId.intValue());

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull().hasSize(2);

        PriceResponse first = response.getBody().get(0);
        assertThat(first.getProductId()).isEqualTo(productId.intValue());
        assertThat(first.getBrandId()).isEqualTo(brandId.intValue());
        assertThat(first.getCurrency()).isEqualTo("EUR");

        verify(useCase, times(1))
                .getApplicablePricesSortedByPriority(brandId, productId, requestDate.toLocalDateTime());
    }

    @Test
    @DisplayName("Should correctly convert OffsetDateTime to LocalDateTime before calling use case")
    void shouldConvertOffsetDateTimeToLocalDateTime() {
        OffsetDateTime offsetDateTime = OffsetDateTime.of(2020, 6, 14, 16, 0, 0, 0, ZoneOffset.ofHours(2));

        when(useCase.getApplicablePricesSortedByPriority(any(), any(), any()))
                .thenReturn(List.of());

        controller.getApplicablePrices(offsetDateTime, 35455, 1);

        ArgumentCaptor<LocalDateTime> captor = ArgumentCaptor.forClass(LocalDateTime.class);
        verify(useCase).getApplicablePricesSortedByPriority(anyLong(), anyLong(), captor.capture());

        LocalDateTime captured = captor.getValue();
        assertThat(captured).isEqualTo(offsetDateTime.toLocalDateTime());
    }
}
