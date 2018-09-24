package com.shopify.developerinternchallenge.models.shop;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shopify.developerinternchallenge.models.order.Order;
import com.shopify.developerinternchallenge.models.product.Product;

@Entity
public class Shop {
	@Id
	@NotBlank
	String name;

	@OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
	@MapKeyColumn(name = Product.ID_COLUMN_NAME)
	Map<Long, Product> products;

	@JsonIgnore
	@NotNull
	@OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
	@MapKeyColumn(name = Order.ID_COLUMN_NAME)
	Map<Long, Order> orders;

	public Shop() {
		this.orders = new HashMap<>();
		this.products = new HashMap<>();
	}

	public Shop(String name) {
		this();
		this.name = name;
	}

	@JsonIgnore
	public PublicShop getPublicShop() {
		return new PublicShop(this);
	}

	public String getName() {
		return name;
	}

	public void addOrder(Order order) {
		this.orders.put(order.getId(), order);
	}
	
	public void deleteOrder(Long orderId) {
		this.orders.remove(orderId);
	}
	
	public Order getOrderById(Long orderId) {
		return this.orders.get(orderId);
	}

	public Map<Long, Order> getOrders() {
		return orders;
	}
	
	public void addProduct(Product product) {
		this.products.put(product.getId(), product);
	}
	
	public void deleteProduct(Product product) {
		this.products.remove(product.getId());
	}

	public Product getProductsById(Long productId) {
		return this.products.get(productId);
	}
	
	public Map<Long, Product> getProducts() {
		return products;
	}
	
	public void setProducts(Map<Long, Product> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return "Shop [name=" + name + "]";
	}
}