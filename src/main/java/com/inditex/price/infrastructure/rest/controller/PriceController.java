package com.inditex.price.infrastructure.rest.controller;

import com.inditex.price.application.usecase.GetApplicablePricesUseCase;
import com.inditex.price.domain.model.Price;
import com.inditex.price.infrastructure.rest.mapper.PriceRestMapper;
import com.inditex.price.api.PricesApi;
import com.inditex.price.model.PriceResponse;

import io.swagger.v3.oas.annotations.Parameter;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
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
    		@Parameter(description = "Application date", required = false)
            OffsetDateTime applicationDate,
            @Parameter(description = "Product ID", required = false)
            Integer productId,
            @Parameter(description = "Brand ID", required = false)
            Integer brandId
    ) {
        log.info("Request received - brandId={}, productId={}, applicationDate={}", brandId, productId, applicationDate);

        List<Price> domainPrices = getApplicablePricesUseCase
                .getApplicablePricesSortedByPriority(
                		brandId != null ? brandId.longValue() : null, 
                		productId != null ? productId.longValue() : null, 
                		applicationDate != null ? applicationDate.toLocalDateTime() : null);
        
        log.debug("Found {} applicable prices for brandId={}, productId={}", domainPrices.size(), brandId, productId);


        return ResponseEntity.ok(PriceRestMapper.toResponseList(domainPrices));
    }
    
    
}
