package com.inditex.price.infrastructure.persistence.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Table;

class PriceEntityTest {

    @Test
    @DisplayName("Should correctly build PriceEntity using Builder pattern")
    void testBuilder() {
        PriceId id = new PriceId(1L, 35455L, 1, LocalDateTime.of(2020, 6, 14, 0, 0));
        LocalDateTime endDate = LocalDateTime.of(2020, 12, 31, 23, 59, 59);
        BigDecimal priceValue = new BigDecimal("35.50");

        PriceEntity entity = PriceEntity.builder()
                .id(id)
                .endDate(endDate)
                .priority(0)
                .price(priceValue)
                .currency("EUR")
                .build();

        assertEquals(id, entity.getId());
        assertEquals(endDate, entity.getEndDate());
        assertEquals(0, entity.getPriority());
        assertEquals(priceValue, entity.getPrice());
        assertEquals("EUR", entity.getCurrency());
    }

    @Test
    @DisplayName("Should correctly set and get values using Lombok-generated setters")
    void testSettersAndGetters() {
        PriceEntity entity = new PriceEntity();

        PriceId id = new PriceId(1L, 35455L, 4, LocalDateTime.of(2020, 6, 15, 16, 0));
        LocalDateTime endDate = LocalDateTime.of(2020, 12, 31, 23, 59, 59);
        BigDecimal price = new BigDecimal("38.95");

        entity.setId(id);
        entity.setEndDate(endDate);
        entity.setPriority(1);
        entity.setPrice(price);
        entity.setCurrency("EUR");

        assertEquals(id, entity.getId());
        assertEquals(endDate, entity.getEndDate());
        assertEquals(1, entity.getPriority());
        assertEquals(price, entity.getPrice());
        assertEquals("EUR", entity.getCurrency());
    }

    @Test
    @DisplayName("Should correctly use all-args constructor")
    void testAllArgsConstructor() {
        PriceId id = new PriceId(1L, 35455L, 2, LocalDateTime.of(2020, 6, 14, 15, 0));
        LocalDateTime endDate = LocalDateTime.of(2020, 6, 14, 18, 30);
        PriceEntity entity = new PriceEntity(id, endDate, 1, new BigDecimal("25.45"), "EUR");

        assertEquals(id, entity.getId());
        assertEquals(endDate, entity.getEndDate());
        assertEquals(1, entity.getPriority());
        assertEquals(new BigDecimal("25.45"), entity.getPrice());
        assertEquals("EUR", entity.getCurrency());
    }

    @Test
    @DisplayName("Should have correct @Table and @Column mappings")
    void testJpaAnnotations() throws NoSuchFieldException {
        // Verify table name
        Table tableAnnotation = PriceEntity.class.getAnnotation(Table.class);
        assertNotNull(tableAnnotation);
        assertEquals("PRICES", tableAnnotation.name());

        // Verify @Column names
        assertEquals("END_DATE", PriceEntity.class.getDeclaredField("endDate")
                .getAnnotation(Column.class).name());
        assertEquals("PRIORITY", PriceEntity.class.getDeclaredField("priority")
                .getAnnotation(Column.class).name());
        assertEquals("PRICE", PriceEntity.class.getDeclaredField("price")
                .getAnnotation(Column.class).name());
        assertEquals("CURR", PriceEntity.class.getDeclaredField("currency")
                .getAnnotation(Column.class).name());
    }

    @Test
    @DisplayName("Should contain @EmbeddedId annotation for PriceId")
    void testEmbeddedIdAnnotation() throws NoSuchFieldException {
        Field field = PriceEntity.class.getDeclaredField("id");
        assertTrue(field.isAnnotationPresent(EmbeddedId.class), "id field should be annotated with @EmbeddedId");
    }
}
