package com.shopify.developerinternchallenge.models.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Product {
	public static final String ID_COLUMN_NAME = "productId";

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = ID_COLUMN_NAME)
	String id;

	@NotBlank
	String name;

	@NotBlank
	@Size(min = 10, max = 256, message = "Product description size should be between 10 and 256")
	String description;

	@PositiveOrZero
	Integer inStock;

	@PositiveOrZero
	Double price;
	
	public Product() {
	}
	
	public Product(String name, String description, Integer inStock, Double price) {
		this();
		this.name = name;
		this.description = description;
		this.inStock = inStock;
		this.price = price;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}

	public Integer getInStock() {
		return inStock;
	}

	public Double getPrice() {
		return price;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", description=" + description + ", inStock=" + inStock
				+ ", price=" + price + "]";
	}
	
}
