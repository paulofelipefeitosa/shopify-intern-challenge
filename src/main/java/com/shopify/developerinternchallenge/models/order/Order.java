package com.shopify.developerinternchallenge.models.order;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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

	@NotNull
	@OneToMany(orphanRemoval = true)
	List<LineItem> lineItems;
	
	public Order() {
		this.lineItems = new ArrayList<>();
	}
	
	public void addLineItem(LineItem lineItem) {
		this.lineItems.add(lineItem);
	}

	public String getId() {
		return id;
	}

	public List<LineItem> getLineItems() {
		return lineItems;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", lineItems=" + lineItems + "]";
	}

}
