package com.shopify.developerinternchallenge.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = Order.TABLE_NAME)
public class Order {
	public static final String ID_COLUMN_NAME = "orderId";
	public static final String TABLE_NAME = "orders_table";
	
	@Id
	@Column(name = ID_COLUMN_NAME)
	@GeneratedValue
	String id;

	@NotNull
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	List<LineItems> lineItems;

	public String getId() {
		return id;
	}

	public List<LineItems> getLineItems() {
		return lineItems;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + "]";
	}

}
