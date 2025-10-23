package com.inditex.price.infrastructure.persistence.adapter;

import com.inditex.price.domain.model.Price;
import com.inditex.price.domain.model.vo.DateRange;
import com.inditex.price.domain.model.vo.Money;
import com.inditex.price.infrastructure.persistence.entity.PriceEntity;
import com.inditex.price.infrastructure.persistence.entity.PriceId;
import com.inditex.price.infrastructure.persistence.repository.SpringDataPriceRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class PriceRepositoryImplTest {

    @Mock
    private SpringDataPriceRepository jpaRepository;

    @InjectMocks
    private PriceRepositoryImpl priceRepository;

    private PriceEntity sampleEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sampleEntity = PriceEntity.builder()
                .id(new PriceId(1L, 35455L, 2, LocalDateTime.of(2020, 6, 14, 15, 0)))
                .endDate(LocalDateTime.of(2020, 6, 14, 18, 30))
                .priority(1)
                .price(new BigDecimal("25.45"))
                .currency("EUR")
                .build();
    }

    @Test
    @DisplayName("Should return mapped domain prices when entities are found")
    void shouldReturnMappedDomainPrices() {
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 16, 0);
        when(jpaRepository.findApplicablePrices(35455L, 1L, date))
                .thenReturn(List.of(sampleEntity));

        List<Price> result = priceRepository.findApplicablePrices(1L, 35455L, date);

        verify(jpaRepository).findApplicablePrices(35455L, 1L, date);
        assertThat(result).hasSize(1);

        Price price = result.get(0);
        assertThat(price.getBrandId()).isEqualTo(1L);
        assertThat(price.getProductId()).isEqualTo(35455L);
        assertThat(price.getPriority()).isEqualTo(1);
        assertThat(price.getMoney()).isEqualTo(Money.of(new BigDecimal("25.45"), "EUR"));
        assertThat(price.getDateRange()).isEqualTo(DateRange.of(
                LocalDateTime.of(2020, 6, 14, 15, 0),
                LocalDateTime.of(2020, 6, 14, 18, 30)
        ));
    }

    @Test
    @DisplayName("Should return empty list when no entities found")
    void shouldReturnEmptyListWhenNoEntitiesFound() {
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 16, 0);
        when(jpaRepository.findApplicablePrices(35455L, 1L, date))
                .thenReturn(List.of());

        List<Price> result = priceRepository.findApplicablePrices(1L, 35455L, date);

        verify(jpaRepository).findApplicablePrices(35455L, 1L, date);
        assertThat(result).isEmpty();
    }
}

