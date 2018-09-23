package com.shopify.developerinternchallenge.models.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class InsufficientProductsAvailableException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2051169925141710982L;

	public InsufficientProductsAvailableException(String msg) {
		super(msg);
	}
}
