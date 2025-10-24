package com.inditex.price.domain.service;

import com.inditex.price.domain.exception.InvalidPriceListException;
import com.inditex.price.domain.model.Price;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PriceDomainService {


    public List<Price> sortApplicablePricesByPriority(List<Price> applicablePrices) {

        if (applicablePrices == null || applicablePrices.isEmpty()) {
            throw new InvalidPriceListException("Price list must not be null or empty");
        }

        return applicablePrices.stream()
                .sorted(Price.byPriorityDescending())
                .toList();
    }
}
