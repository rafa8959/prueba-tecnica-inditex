package com.inditex.price.application.usecase;

import com.inditex.price.domain.model.Price;
import com.inditex.price.domain.repository.PriceRepository;
import com.inditex.price.domain.service.PriceDomainService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetApplicablePricesUseCaseTest {

    @Mock
    private PriceRepository priceRepository;

    @Mock
    private PriceDomainService priceDomainService;

    @InjectMocks
    private GetApplicablePricesUseCase useCase;

    @Test
    @DisplayName("Should retrieve prices from repository and return them sorted by priority")
    void shouldRetrieveAndSortApplicablePrices() {
        Long brandId = 1L;
        Long productId = 35455L;
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 10, 0);

        List<Price> unsortedPrices = List.of(mock(Price.class), mock(Price.class));
        List<Price> sortedPrices = List.of(mock(Price.class), mock(Price.class));

        when(priceRepository.findApplicablePrices(brandId, productId, date))
                .thenReturn(unsortedPrices);
        when(priceDomainService.sortApplicablePricesByPriority(unsortedPrices))
                .thenReturn(sortedPrices);

        List<Price> result = useCase.getApplicablePricesSortedByPriority(brandId, productId, date);

        assertThat(result).isEqualTo(sortedPrices);
        verify(priceRepository, times(1)).findApplicablePrices(brandId, productId, date);
        verify(priceDomainService, times(1)).sortApplicablePricesByPriority(unsortedPrices);
        verifyNoMoreInteractions(priceRepository, priceDomainService);
    }

    @Test
    @DisplayName("Should handle empty list returned from repository gracefully")
    void shouldHandleEmptyRepositoryResult() {
        Long brandId = 1L;
        Long productId = 35455L;
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 10, 0);

        when(priceRepository.findApplicablePrices(brandId, productId, date))
                .thenReturn(List.of());
        when(priceDomainService.sortApplicablePricesByPriority(List.of()))
                .thenReturn(List.of());

        List<Price> result = useCase.getApplicablePricesSortedByPriority(brandId, productId, date);

        assertThat(result).isEmpty();
        verify(priceRepository).findApplicablePrices(brandId, productId, date);
        verify(priceDomainService).sortApplicablePricesByPriority(List.of());
    }
}
