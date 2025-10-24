package com.inditex.price.domain.exception;

public class InvalidMoneyAmountException extends DomainException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 749264694089183445L;

	public InvalidMoneyAmountException(String message) {
        super(message);
    }
}
