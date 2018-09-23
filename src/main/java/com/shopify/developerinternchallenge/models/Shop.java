package com.shopify.developerinternchallenge.models;

import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Shop {
	@Id
	@NotBlank
	@GeneratedValue
	String id;

	@NotBlank
	String name;

	@NotNull
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	Stock stock;
	
	@NotNull
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

	@Override
	public String toString() {
		return "Shop [id=" + id + ", name=" + name + "]";
	}
}