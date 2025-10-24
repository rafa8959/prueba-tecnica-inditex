package com.inditex.price.integration;

import com.intuit.karate.junit5.Karate;

class PriceFeatureRunner {

    @Karate.Test
    Karate testPrices() {
        // Ejecuta todos los .feature dentro de resources/karate
        return Karate.run("classpath:karate/prices.feature")
                     .relativeTo(getClass());
    }
}

