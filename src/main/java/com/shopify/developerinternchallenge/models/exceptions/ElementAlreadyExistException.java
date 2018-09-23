package com.shopify.developerinternchallenge.models.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ElementAlreadyExistException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1571522928312688529L;

	public ElementAlreadyExistException(String msg) {
		super(msg);
	}
	
}
