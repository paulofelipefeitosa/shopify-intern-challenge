package com.shopify.developerinternchallenge.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class LineItem {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	String id;
	
	@NotNull
	@OneToOne()
	Product product;
	
	@Positive
	Integer amount;
	
	public LineItem() {
	}
	
	public LineItem(Product product, Integer amount) {
		this();
		this.product = product;
		this.amount = amount;
	}

	public String getId() {
		return id;
	}

	public Product getProduct() {
		return product;
	}

	public Integer getAmount() {
		return amount;
	}

	@Override
	public String toString() {
		return "LineItem [id=" + id + ", product=" + product + ", amount=" + amount + "]";
	}
}
