package com.shopify.developerinternchallenge.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
public class LineItems {
	@NotNull
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn
	Product product;
	
	@Positive
	Integer amount;
}
