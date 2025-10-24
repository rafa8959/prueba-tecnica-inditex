package com.inditex.price.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PriceId implements Serializable {

	private static final long serialVersionUID = -2001599426509823017L;
	
    @Column(name = "BRAND_ID", nullable = false)
	private Long brandId;
    
    @Column(name = "PRODUCT_ID", nullable = false)
    private Long productId;
    
    @Column(name = "PRICE_LIST", nullable = false)
    private Integer priceList;
    
}