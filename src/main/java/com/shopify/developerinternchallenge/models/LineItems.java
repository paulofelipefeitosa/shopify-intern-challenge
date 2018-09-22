package com.shopify.developerinternchallenge.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
public class LineItems {
	@Id
	@GeneratedValue
	String id;
	
	@NotNull
	@OneToOne(cascade = CascadeType.ALL)
	Product product;
	
	@Positive
	Integer amount;
}
