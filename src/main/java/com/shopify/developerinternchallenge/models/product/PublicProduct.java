package com.shopify.developerinternchallenge.models.product;

public class PublicProduct {
	Long id;
	String name;
	String description;
	Integer availableAmount;
	Double price;
	
	public PublicProduct(Product product) {
		this.id = product.getId();
		this.name = product.getName();
		this.description = product.getDescription();
		this.availableAmount = product.getAvailableAmount();
		this.price = product.getPrice();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Integer getAvailableAmount() {
		return availableAmount;
	}

	public Double getPrice() {
		return price;
	}
	
}
