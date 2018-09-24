package com.shopify.developerinternchallenge.models.product;

public class PublicProduct {
	Long id;
	String name;
	String description;
	Integer availableAmount;
	Double price;
	
	public PublicProduct() {
	}
	
	public PublicProduct(Product product) {
		this();
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

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setAvailableAmount(Integer availableAmount) {
		this.availableAmount = availableAmount;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
}
