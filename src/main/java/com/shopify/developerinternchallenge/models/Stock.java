package com.shopify.developerinternchallenge.models;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Stock {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	String id;
	
	@OneToMany(orphanRemoval = true)
	@MapKeyColumn(name = Product.ID_COLUMN_NAME)
	Map<String, Product> products;
	
	public Stock() {
		this.products = new HashMap<>();
	}
	
	public void addProduct(Product product) {
		this.products.put(product.getId(), product);
	}

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
