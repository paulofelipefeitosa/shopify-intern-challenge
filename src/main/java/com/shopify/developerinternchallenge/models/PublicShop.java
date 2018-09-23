package com.shopify.developerinternchallenge.models;

public class PublicShop {
	String id;
	String name;

	public PublicShop(Shop shop) {
		this.id = shop.getId();
		this.name = shop.getName();
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
