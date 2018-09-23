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

import com.shopify.developerinternchallenge.models.order.Order;
import com.shopify.developerinternchallenge.models.product.Product;

@Entity
public class Shop {
	@Id
	@NotBlank
	String name;

	@OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
	@MapKeyColumn(name = Product.ID_COLUMN_NAME)
	Map<String, Product> products;

	@NotNull
	@OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
	@MapKeyColumn(name = Order.ID_COLUMN_NAME)
	Map<String, Order> orders;

	public Shop() {
		this.orders = new HashMap<>();
		this.products = new HashMap<>();
	}

	public Shop(String name) {
		this();
		this.name = name;
	}

	public PublicShop getPublicShop() {
		return new PublicShop(this);
	}

	public String getName() {
		return name;
	}

	public void addOrder(Order order) {
		this.orders.put(order.getId(), order);
	}
	
	public void deleteOrder(String orderId) {
		this.orders.remove(orderId);
	}

	public Map<String, Order> getOrders() {
		return orders;
	}
	
	public void addProduct(Product product) {
		this.products.put(product.getId(), product);
	}
	
	public void deleteProduct(Product product) {
		this.products.remove(product.getId());
	}

	public Product getProductsById(String productId) {
		return this.products.get(productId);
	}
	
	public Map<String, Product> getProducts() {
		return products;
	}
	
	public void setProducts(Map<String, Product> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return "Shop [name=" + name + "]";
	}
}