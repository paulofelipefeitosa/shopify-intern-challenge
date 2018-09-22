package com.shopify.developerinternchallenge.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Entity
public class Product {
	public static final String ID_COLUMN_NAME = "productId";

	@Id
	@NotBlank
	@Column(name = ID_COLUMN_NAME)
	String id;

	@NotBlank
	String name;

	@NotBlank
	@Size(min = 10, max = 256, message = "Product description size should be between 10 and 256")
	String description;

	@PositiveOrZero
	Integer inStock;

	@PositiveOrZero
	Double price;

}
