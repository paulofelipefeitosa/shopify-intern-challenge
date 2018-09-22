package com.shopify.developerinternchallenge.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Order {
	public static final String ID_COLUMN_NAME = "orderId";
	
	@Id
	@NotBlank
	@Column(name = ID_COLUMN_NAME)
	String id;

	@NotNull
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn
	List<LineItems> lineItems;
}
