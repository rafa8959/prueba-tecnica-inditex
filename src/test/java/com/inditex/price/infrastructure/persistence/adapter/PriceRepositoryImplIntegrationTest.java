package com.inditex.price.infrastructure.persistence.adapter;

import com.inditex.price.domain.model.Price;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class PriceRepositoryImplIntegrationTest {

    @Autowired
    private PriceRepositoryImpl repository;

    @Test
    @DisplayName("Should load applicable prices from H2 database and map them correctly to domain model")
    void shouldLoadPricesFromDatabase() {
    	
        Long brandId = 1L;
        Long productId = 35455L;
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);

        List<Price> prices = repository.findApplicablePrices(brandId, productId, applicationDate);

        assertThat(prices).isNotEmpty();
        assertThat(prices)
                .allSatisfy(price -> {
                    assertThat(price.getProductId()).isEqualTo(productId);
                    assertThat(price.getBrandId()).isEqualTo(brandId);
                    assertThat(price.getMoney().getCurrency()).isEqualTo("EUR");
                });
    }
}
