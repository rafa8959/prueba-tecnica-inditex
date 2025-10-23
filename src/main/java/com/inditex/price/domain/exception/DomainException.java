package com.inditex.price.domain.exception;

public abstract class DomainException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 744653620086304300L;

	protected DomainException(String message) {
        super(message);
    }
}
