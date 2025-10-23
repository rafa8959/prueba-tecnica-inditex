package com.inditex.price.domain.model;

import java.time.LocalDateTime;
import java.util.Comparator;

import com.inditex.price.domain.exception.DomainValidationException;
import com.inditex.price.domain.model.vo.DateRange;
import com.inditex.price.domain.model.vo.Money;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Price {
	private final Long brandId;
	private final Long productId;
	private final Integer priceList;
	private final DateRange dateRange;
	private final Integer priority;
	private final Money money;

	private Price(Long brandId, Long productId, Integer priceList, DateRange dateRange, Integer priority,
			Money money) {

		if (brandId == null || productId == null)
			throw new DomainValidationException("BrandId and ProductId must not be null");

		if (priceList == null || priceList < 0)
			throw new DomainValidationException("PriceList must be >= 0");

		if (priority == null || priority < 0)
			throw new DomainValidationException("Priority must be >= 0");

		if (dateRange == null)
			throw new DomainValidationException("DateRange must not be null");

		if (money == null)
			throw new DomainValidationException("Money must not be null");

		this.brandId = brandId;
		this.productId = productId;
		this.priceList = priceList;
		this.dateRange = dateRange;
		this.priority = priority;
		this.money = money;
	}
	
    @Builder(builderMethodName = "create")
    public static Price newPrice(Long brandId, Long productId, Integer priceList,
                                 DateRange dateRange, Integer priority, Money money) {
        return new Price(brandId, productId, priceList, dateRange, priority, money);
    }

	public boolean isApplicableAt(LocalDateTime date) {
		return dateRange.includes(date);
	}

	public boolean hasHigherPriorityThan(Price other) {
		return this.priority > other.priority;
	}

	public static Comparator<Price> byPriorityDescending() {
	    return (p1, p2) -> p1.hasHigherPriorityThan(p2) ? -1 : 1;

	}

}
