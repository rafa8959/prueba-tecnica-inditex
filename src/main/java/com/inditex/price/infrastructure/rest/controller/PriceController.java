package com.inditex.price.infrastructure.rest.controller;

import com.inditex.price.application.usecase.GetApplicablePricesUseCase;
import com.inditex.price.domain.model.Price;
import com.inditex.price.infrastructure.rest.mapper.PriceRestMapper;
import com.inditex.price.api.PricesApi;
import com.inditex.price.model.PriceResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.time.LocalDateTime;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PriceController implements PricesApi {

    private final GetApplicablePricesUseCase getApplicablePricesUseCase;

    @Override
    public ResponseEntity<List<PriceResponse>> getApplicablePrices(
            OffsetDateTime applicationDate,
            Integer productId,
            Integer brandId
    ) {
        log.info("Request received - brandId={}, productId={}, applicationDate={}", brandId, productId, applicationDate);

        LocalDateTime localDateTime = applicationDate.toLocalDateTime();

        List<Price> domainPrices = getApplicablePricesUseCase
                .getApplicablePricesSortedByPriority(brandId.longValue(), productId.longValue(), localDateTime);
        
        log.debug("Found {} applicable prices for brandId={}, productId={}", domainPrices.size(), brandId, productId);


        return ResponseEntity.ok(PriceRestMapper.toResponseList(domainPrices));
    }
    
    
}
