package com.inditex.price.application.usecase;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inditex.price.domain.model.Price;
import com.inditex.price.domain.repository.PriceRepository;
import com.inditex.price.domain.service.PriceDomainService;

@Service
@Transactional(readOnly = true)
public class GetApplicablePricesUseCase {

	private final PriceRepository priceRepository;
    private final PriceDomainService priceDomainService;

    public GetApplicablePricesUseCase(PriceDomainService priceDomainService, PriceRepository priceRepository) {
		this.priceRepository = priceRepository;
		this.priceDomainService = priceDomainService;
    }

    public List<Price> getApplicablePricesSortedByPriority(Long brandId, Long productId, LocalDateTime applicationDate) {
    	List<Price> applicablePrices = priceRepository.findApplicablePrices(brandId, productId, applicationDate);
        return priceDomainService.sortApplicablePricesByPriority(applicablePrices);
    }
}
