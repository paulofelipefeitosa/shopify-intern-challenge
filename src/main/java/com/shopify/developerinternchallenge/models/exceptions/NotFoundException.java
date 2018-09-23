package com.shopify.developerinternchallenge.models.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8110294403718102219L;

	public NotFoundException(String msg) {
		super(msg);
	}
	
}
