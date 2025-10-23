package com.inditex.price.infrastructure.persistence.adapter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.inditex.price.domain.model.Price;
import com.inditex.price.domain.repository.PriceRepository;
import com.inditex.price.infrastructure.persistence.entity.PriceEntity;
import com.inditex.price.infrastructure.persistence.mapper.PriceEntityMapper;
import com.inditex.price.infrastructure.persistence.repository.SpringDataPriceRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PriceRepositoryImpl implements PriceRepository{

    private final SpringDataPriceRepository jpaRepository;

    @Override
    public List<Price> findApplicablePrices(Long brandId, Long productId, LocalDateTime applicationDate) {
        List<PriceEntity> entities = jpaRepository.findApplicablePrices(productId, brandId, applicationDate);

        return entities.stream()
                .map(PriceEntityMapper::toDomain)
                .collect(Collectors.toList());
    }
}