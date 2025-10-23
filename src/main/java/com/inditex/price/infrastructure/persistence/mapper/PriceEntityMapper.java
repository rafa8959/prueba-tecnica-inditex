package com.inditex.price.infrastructure.persistence.mapper;

import com.inditex.price.domain.model.Price;
import com.inditex.price.domain.model.vo.DateRange;
import com.inditex.price.domain.model.vo.Money;
import com.inditex.price.infrastructure.persistence.entity.PriceEntity;

public class PriceEntityMapper {

    public static Price toDomain(PriceEntity entity) {
    	if(entity == null) {
    		return null;
    	}
    	if (entity.getId() == null) {
    	    throw new IllegalArgumentException("PriceEntity.id must not be null");
    	}
        return Price.builder()
                .brandId(entity.getId().getBrandId())
                .productId(entity.getId().getProductId())
                .priceList(entity.getId().getPriceList())
                .dateRange(DateRange.of(entity.getId().getStartDate(), entity.getEndDate()))
                .priority(entity.getPriority())
                .money(Money.of(entity.getPrice(), entity.getCurrency()))
                .build();
    }
}