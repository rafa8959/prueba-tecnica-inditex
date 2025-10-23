package com.inditex.price.domain.service;

import com.inditex.price.domain.exception.PriceNotFoundException;
import com.inditex.price.domain.model.Price;
import com.inditex.price.domain.repository.PriceRepository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PriceDomainService {

    private final PriceRepository priceRepository;

    public PriceDomainService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public List<Price> execute(Long brandId, Long productId, LocalDateTime applicationDate) {
        List<Price> prices = priceRepository.findApplicablePrices(brandId, productId, applicationDate);

        if (prices.isEmpty()) {
            throw new PriceNotFoundException(brandId, productId, applicationDate.toString());
        }
        
        prices.sort(Price.byPriorityDescending());

        return prices;
    }
}
