package com.inditex.price.infrastructure.persistence.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PriceIdTest {

    @Test
    @DisplayName("should create PriceId with all arguments and getters should return correct values")
    void testAllArgsConstructorAndGetters() {
        LocalDateTime startDate = LocalDateTime.of(2020, 6, 14, 0, 0);

        PriceId priceId = new PriceId(1L, 35455L, 2, startDate);

        assertEquals(1L, priceId.getBrandId());
        assertEquals(35455L, priceId.getProductId());
        assertEquals(2, priceId.getPriceList());
        assertEquals(startDate, priceId.getStartDate());
    }

    @Test
    @DisplayName("should set and get values using Lombok setters")
    void testSettersAndGetters() {
        PriceId priceId = new PriceId();
        LocalDateTime startDate = LocalDateTime.of(2020, 6, 15, 12, 0);

        priceId.setBrandId(1L);
        priceId.setProductId(35455L);
        priceId.setPriceList(4);
        priceId.setStartDate(startDate);

        assertEquals(1L, priceId.getBrandId());
        assertEquals(35455L, priceId.getProductId());
        assertEquals(4, priceId.getPriceList());
        assertEquals(startDate, priceId.getStartDate());
    }

    @Test
    @DisplayName("should correctly compare two equal PriceId objects")
    void testEqualsAndHashCodeEqualObjects() {
        LocalDateTime startDate = LocalDateTime.of(2020, 6, 14, 0, 0);
        PriceId id1 = new PriceId(1L, 35455L, 1, startDate);
        PriceId id2 = new PriceId(1L, 35455L, 1, startDate);

        assertEquals(id1, id2);
        assertEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    @DisplayName("should not consider different PriceIds as equal")
    void testEqualsAndHashCodeDifferentObjects() {
        PriceId id1 = new PriceId(1L, 35455L, 1, LocalDateTime.of(2020, 6, 14, 0, 0));
        PriceId id2 = new PriceId(1L, 35455L, 2, LocalDateTime.of(2020, 6, 14, 0, 0));

        assertNotEquals(id1, id2);
        assertNotEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    @DisplayName("should be serializable and deserializable")
    void testSerializable() throws Exception {
        PriceId original = new PriceId(1L, 35455L, 1, LocalDateTime.of(2020, 6, 14, 0, 0));

        // Serialize
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteStream);
        out.writeObject(original);

        // Deserialize
        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(byteStream.toByteArray()));
        PriceId deserialized = (PriceId) in.readObject();

        assertEquals(original, deserialized);
        assertEquals(original.hashCode(), deserialized.hashCode());
    }
}
