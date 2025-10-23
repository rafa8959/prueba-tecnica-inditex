package com.inditex.price.infrastructure.persistence.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.inditex.price.domain.exception.InvalidMoneyAmountException;
import com.inditex.price.domain.model.Price;
import com.inditex.price.infrastructure.persistence.entity.PriceEntity;
import com.inditex.price.infrastructure.persistence.entity.PriceId;

class PriceEntityMapperTest {

    @Test
    @DisplayName("Should correctly map PriceEntity to domain Price")
    void testToDomainMapsAllFieldsCorrectly() {
        PriceId id = new PriceId(1L, 35455L, 2, LocalDateTime.of(2020, 6, 14, 15, 0));
        PriceEntity entity = PriceEntity.builder()
                .id(id)
                .endDate(LocalDateTime.of(2020, 6, 14, 18, 30))
                .priority(1)
                .price(new BigDecimal("25.45"))
                .currency("EUR")
                .build();

        Price price = PriceEntityMapper.toDomain(entity);

        assertNotNull(price);
        assertEquals(1L, price.getBrandId());
        assertEquals(35455L, price.getProductId());
        assertEquals(2, price.getPriceList());
        assertEquals(1, price.getPriority());
        assertEquals(new BigDecimal("25.45"), price.getMoney().getAmount());
        assertEquals("EUR", price.getMoney().getCurrency());

        assertEquals(LocalDateTime.of(2020, 6, 14, 15, 0), price.getDateRange().getStartDate());
        assertEquals(LocalDateTime.of(2020, 6, 14, 18, 30), price.getDateRange().getEndDate());
    }

    @Test
    @DisplayName("Should return null when entity is null")
    void testToDomainReturnsNullWhenEntityIsNull() {
        assertNull(PriceEntityMapper.toDomain(null));
    }

    @Test
    @DisplayName("Should throw exception if PriceEntity has invalid dates or money")
    void testToDomainThrowsWhenInvalid() {
        PriceId id = new PriceId(1L, 35455L, 1, LocalDateTime.of(2020, 6, 14, 18, 30));
        PriceEntity entity = PriceEntity.builder()
                .id(id)
                .endDate(LocalDateTime.of(2020, 6, 14, 15, 0))
                .priority(1)
                .price(new BigDecimal("10.00"))
                .currency("EUR")
                .build();

        assertThrows(RuntimeException.class, () -> PriceEntityMapper.toDomain(entity));
    }
    
    @Test
    @DisplayName("Should throw NullPointerException when required fields are null")
    void testToDomainWithNullFields() {
        PriceEntity entityWithoutId = PriceEntity.builder()
                .endDate(LocalDateTime.of(2020, 6, 14, 18, 30))
                .priority(1)
                .price(new BigDecimal("25.45"))
                .currency("EUR")
                .build();

        assertThrows(IllegalArgumentException.class, () -> PriceEntityMapper.toDomain(entityWithoutId));

        PriceId id = new PriceId(1L, 35455L, 2, LocalDateTime.of(2020, 6, 14, 15, 0));
        PriceEntity entityWithNullCurrency = PriceEntity.builder()
                .id(id)
                .endDate(LocalDateTime.of(2020, 6, 14, 18, 30))
                .priority(1)
                .price(new BigDecimal("25.45"))
                .currency(null)
                .build();

        assertThrows(InvalidMoneyAmountException.class, () -> PriceEntityMapper.toDomain(entityWithNullCurrency));
    }

}
