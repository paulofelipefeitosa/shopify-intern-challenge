package com.shopify.developerinternchallenge.models;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Shop {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	String id;

	@NotBlank
	String name;

	@NotNull
	@OneToOne(orphanRemoval = true)
	Stock stock;

	@NotNull
	@OneToMany(orphanRemoval = true)
	@MapKeyColumn(name = Order.ID_COLUMN_NAME)
	Map<String, Order> orders;
	
	public Shop() {
		this.orders = new HashMap<>();
	}

	public Shop(String name, Stock stock) {
		this();
		this.name = name;
		this.stock = stock;
	}

	public void addOrder(Order order) {
		this.orders.put(order.getId(), order);
	}

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