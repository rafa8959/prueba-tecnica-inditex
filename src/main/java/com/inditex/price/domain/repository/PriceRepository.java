package com.inditex.price.domain.repository;
import java.time.LocalDateTime;
import java.util.List;

import com.inditex.price.domain.model.Price;

public interface PriceRepository {

    /**
     * Finds all prices for a given product and brand that are applicable at a specific date.
     *
     * @param brandId   the brand identifier (e.g. 1 = ZARA)
     * @param productId the product identifier
     * @param date      the application date
     * @return a list of matching prices (may be empty if none found)
     */
    List<Price> findApplicablePrices(Long brandId, Long productId, LocalDateTime date);

}
