package com.inditex.price.infrastructure.rest.mapper;

import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

import com.inditex.price.domain.model.Price;
import com.inditex.price.model.PriceResponse;

public class PriceRestMapper {

    public static List<PriceResponse> toResponseList(List<Price> prices) {
    	if(prices == null || prices.isEmpty()) {
    		return List.of();
    	}
        List<PriceResponse> responses = prices.stream()
                .map(price -> toResponse(price, false))
                .collect(Collectors.toList());

        if (!responses.isEmpty()) {
            responses.get(0).setApplied(true);
        }

        return responses;
    }

    public static PriceResponse toResponse(Price price, boolean applied) {
        PriceResponse response = new PriceResponse();
        response.setProductId(price.getProductId().intValue());
        response.setBrandId(price.getBrandId().intValue());
        response.setPriceList(price.getPriceList());
        response.setStartDate(price.getDateRange().getStartDate().atOffset(ZoneOffset.UTC));
        response.setEndDate(price.getDateRange().getEndDate().atOffset(ZoneOffset.UTC));
        response.setPriority(price.getPriority());
        response.setPrice(price.getMoney().getAmount().doubleValue());
        response.setCurrency(price.getMoney().getCurrency());
        response.setApplied(applied);
        return response;
    }
}
