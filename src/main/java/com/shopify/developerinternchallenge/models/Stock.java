package com.shopify.developerinternchallenge.models;

import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

@Entity
public class Stock {
	@Id
	@NotBlank
	String id;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@MapKeyColumn(name = Product.ID_COLUMN_NAME)
	Map<String, Product> products;

	public String getId() {
		return id;
	}

	public Product getProductsById(String productId) {
		return this.products.get(productId);
	}
	
	public Map<String, Product> getProducts() {
		return products;
	}

	@Override
	public String toString() {
		return "Stock [id=" + id + "]";
	}
	
}
