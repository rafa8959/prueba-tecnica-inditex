package com.inditex.price.application.usecase;

import com.inditex.price.domain.model.Price;
import com.inditex.price.domain.repository.PriceRepository;
import com.inditex.price.domain.service.PriceDomainService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class GetApplicablePricesUseCaseTest {

    @Mock
    private PriceRepository priceRepository;

    @Mock
    private PriceDomainService priceDomainService;

    @InjectMocks
    private GetApplicablePricesUseCase useCase;

    private final LocalDateTime date = LocalDateTime.of(2020, 6, 14, 10, 0, 0);
    private final Price price1 = mock(Price.class);
    private final Price price2 = mock(Price.class);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should delegate to domain service when all filters are present")
    void shouldDelegateToDomainServiceWhenAllFiltersArePresent() {
        when(priceRepository.findApplicablePrices(1L, 35455L, date))
                .thenReturn(List.of(price1, price2));
        when(priceDomainService.filterHighestPriorityPrices(anyList()))
                .thenReturn(List.of(price1));

        List<Price> result = useCase.getApplicablePricesSortedByPriority(1L, 35455L, date);

        verify(priceRepository).findApplicablePrices(1L, 35455L, date);
        verify(priceDomainService).filterHighestPriorityPrices(List.of(price1, price2));
        assertThat(result).containsExactly(price1);
    }


    @Test
    @DisplayName("Should return repository results directly when some filters are missing")
    void shouldReturnRepositoryResultsDirectlyWhenFiltersMissing() {
        when(priceRepository.findApplicablePrices(null, 35455L, null))
                .thenReturn(List.of(price1, price2));

        List<Price> result = useCase.getApplicablePricesSortedByPriority(null, 35455L, null);

 
        verify(priceRepository).findApplicablePrices(null, 35455L, null);
        verify(priceDomainService, never()).filterHighestPriorityPrices(anyList());
        assertThat(result).containsExactly(price1, price2);
    }

    @Test
    @DisplayName("Should return empty list if repository returns no prices")
    void shouldReturnEmptyListIfRepositoryReturnsNone() {
   
        when(priceRepository.findApplicablePrices(1L, 35455L, date))
                .thenReturn(List.of());


        List<Price> result = useCase.getApplicablePricesSortedByPriority(1L, 35455L, date);


        assertThat(result).isEmpty();
    }


    @Test
    @DisplayName("Should return all prices when no filters are provided")
    void shouldReturnAllPricesWhenNoFiltersProvided() {
        when(priceRepository.findApplicablePrices(null, null, null))
                .thenReturn(List.of(price1, price2));


        List<Price> result = useCase.getApplicablePricesSortedByPriority(null, null, null);


        verify(priceRepository).findApplicablePrices(null, null, null);
        verify(priceDomainService, never()).filterHighestPriorityPrices(anyList());
        assertThat(result).hasSize(2);
    }
}
