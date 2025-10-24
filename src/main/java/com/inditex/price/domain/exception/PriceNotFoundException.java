package com.inditex.price.domain.exception;

public class PriceNotFoundException extends DomainException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4227710742455187395L;
	private final Long brandId;
    private final Long productId;
    private final String date;

    public PriceNotFoundException(Long brandId, Long productId, String date) {
        super(String.format("No price found for brand %d, product %d, at %s", brandId, productId, date));
        this.brandId = brandId;
        this.productId = productId;
        this.date = date;
    }

    public Long getBrandId() {
        return brandId;
    }

    public Long getProductId() {
        return productId;
    }

    public String getDate() {
        return date;
    }
}
