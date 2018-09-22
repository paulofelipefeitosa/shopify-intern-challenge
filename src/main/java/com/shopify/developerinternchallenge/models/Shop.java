package com.shopify.developerinternchallenge.models;

import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Shop {
	@Id
	@NotBlank
	String id;

	@NotBlank
	String name;

	@NotNull
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn
	Stock stock;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@MapKeyColumn(name = Order.ID_COLUMN_NAME)
	Map<String, Order> orders;

	public PublicShop getPublicShop() {
		return new PublicShop(this);
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Stock getStock() {
		return stock;
	}

	public Map<String, Order> getOrders() {
		return orders;
	}
}