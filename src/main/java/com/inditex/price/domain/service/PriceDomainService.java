package com.inditex.price.domain.service;

import com.inditex.price.domain.model.Price;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class PriceDomainService {


    public List<Price> filterHighestPriorityPrices(List<Price> applicablePrices) {

        if (applicablePrices == null || applicablePrices.isEmpty()) {
            return new ArrayList<>();
        }
        
        Map<String, List<Price>> grouped = applicablePrices.stream()
                .collect(Collectors.groupingBy(p -> p.getBrandId() + "-" + p.getProductId()));

        //Dentro de cada grupo, obtener el Price con mayor prioridad
        return grouped.values().stream()
        		.flatMap(group -> {
                    int maxPriority = group.stream()
                            .mapToInt(Price::getPriority)
                            .max()
                            .orElse(Integer.MIN_VALUE);
                    // Devolvemos todos los precios con esa prioridad máxima
                    return group.stream()
                            .filter(p -> p.getPriority() == maxPriority);
                })
                .sorted(Comparator.comparing(Price::getBrandId)
                        .thenComparing(Price::getProductId))
                .toList();
    }
    
}
