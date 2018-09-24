package com.shopify.developerinternchallenge.models.product;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shopify.developerinternchallenge.models.lineitems.LineItem;

@Entity
public class Product {
	public static final String ID_COLUMN_NAME = "productId";

	@Id
	@GeneratedValue
	@Column(name = ID_COLUMN_NAME)
	Long id;

	@NotBlank
	String name;

	@NotBlank
	@Size(min = 10, max = 256, message = "Product description size should be between 10 and 256")
	String description;

	@PositiveOrZero
	Integer inStock;

	@PositiveOrZero
	Double price;
	
	@JsonIgnore
	@NotNull
	@OneToMany(fetch = FetchType.EAGER)
	List<LineItem> lineItems;
	
	public Product() {
		this.lineItems = new ArrayList<>();
	}
	
	public Product(String name, String description, Integer inStock, Double price) {
		this();
		this.name = name;
		this.description = description;
		this.inStock = inStock;
		this.price = price;
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
		return inStock - sumLineItemsAmount();
	}
	
	private Integer sumLineItemsAmount() {
		Integer result = 0;
		for(LineItem lineItem : this.lineItems) {
			result += lineItem.getAmount();
		}
		return result;
	}
	
	public void add2Stock(Integer add2Stock) {
		this.inStock += add2Stock;
	}

	public Double getPrice() {
		return price;
	}

	public List<LineItem> getLineItems() {
		return lineItems;
	}
	
	public void addLineItem(LineItem lineItem) {
		this.lineItems.add(lineItem);
	}
	
	public LineItem getLineItemWithId(Long lineItemId) {
		for (LineItem lineItem : this.lineItems) {
			if (lineItem.getId() == lineItemId) {
				return lineItem;
			}
		}
		return null;
	}

	public void deleteLineItemWithId(Long lineItemId) {
		LineItem lineItem = getLineItemWithId(lineItemId);
		if (lineItem != null) {
			this.lineItems.remove(lineItem);
		}
	}
	
	public PublicProduct publicProduct() {
		return new PublicProduct(this);
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", description=" + description + ", inStock=" + inStock
				+ ", price=" + price + "]";
	}
	
}
