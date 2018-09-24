package com.shopify.developerinternchallenge.models.order;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.shopify.developerinternchallenge.models.lineitems.LineItem;

@Entity
@Table(name = Order.TABLE_NAME)
public class Order {
	public static final String ID_COLUMN_NAME = "orderId";
	public static final String TABLE_NAME = "orders_table";

	@Id
	@GeneratedValue
	@Column(name = ID_COLUMN_NAME)
	Long id;

	@PositiveOrZero
	Double value;
	
	@NotNull
	@OneToMany(fetch = FetchType.EAGER)
	List<LineItem> lineItems;
	
	public Order() {
		this.lineItems = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}
	
	public void addLineItem(LineItem lineItem) {
		this.lineItems.add(lineItem);
		calculateValue();
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
			calculateValue();
		}
	}

	public List<LineItem> getLineItems() {
		return lineItems;
	}

	public void setLineItems(List<LineItem> lineItems) {
		this.lineItems = lineItems;
		calculateValue();
	}
	
	private void calculateValue() {
		this.value = 0.0;
		for(LineItem lineItem : this.lineItems) {
			if(lineItem.getValue() != null) {
				this.value += lineItem.getValue();
			}
		}
	}

	public Double getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", lineItems=" + lineItems + "]";
	}

}
