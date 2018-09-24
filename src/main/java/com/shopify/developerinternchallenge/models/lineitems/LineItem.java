package com.shopify.developerinternchallenge.models.lineitems;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import com.shopify.developerinternchallenge.models.product.Product;

@Entity
public class LineItem {
	@Id
	@GeneratedValue
	Long id;

	@NotNull
	@ManyToOne
	Product product;

	@Positive
	Integer amount;

	@PositiveOrZero
	Double value;

	public LineItem() {
	}

	public LineItem(Product product, Integer amount) {
		this();
		this.product = product;
		this.amount = amount;
		calculateValue();
	}

	private void calculateValue() {
		this.value = this.product.getPrice() * this.amount;
	}

	public Double getValue() {
		return value;
	}

	public Long getId() {
		return id;
	}

	public Product getProduct() {
		return product;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
		calculateValue();
	}

	@Override
	public String toString() {
		return "LineItem [id=" + id + ", product=" + product + ", amount=" + amount + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LineItem other = (LineItem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
