package com.shopify.developerinternchallenge.models.shop;

public class PublicShop {
	String name;

	public PublicShop(Shop shop) {
		this.name = shop.getName();
	}

	public String getName() {
		return name;
	}
}
