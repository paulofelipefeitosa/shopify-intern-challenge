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

import org.hibernate.annotations.GenericGenerator;

import com.shopify.developerinternchallenge.models.lineitems.LineItem;

@Entity
@Table(name = Order.TABLE_NAME)
public class Order {
	public static final String ID_COLUMN_NAME = "orderId";
	public static final String TABLE_NAME = "orders_table";

	@Id
	@Column(name = ID_COLUMN_NAME)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	String id;

	@PositiveOrZero
	Double value;
	
	@NotNull
	@OneToMany(fetch = FetchType.EAGER)
	List<LineItem> lineItems;
	
	public Order() {
		this.lineItems = new ArrayList<>();
	}

	public String getId() {
		return id;
	}
	
	public void addLineItem(LineItem lineItem) {
		this.lineItems.add(lineItem);
		calculateValue();
	}

	public LineItem getLineItemWithId(String lineItemId) {
		for (LineItem lineItem : this.lineItems) {
			if (lineItem.getId() == lineItemId) {
				return lineItem;
			}
		}
		return null;
	}

	public void deleteLineItemWithId(String lineItemId) {
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
			this.value += lineItem.getValue();
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
