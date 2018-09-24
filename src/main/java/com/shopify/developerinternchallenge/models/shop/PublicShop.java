package com.shopify.developerinternchallenge.models.shop;

public class PublicShop {
	String name;

	public PublicShop() {
		
	}
	
	public PublicShop(Shop shop) {
		this();
		this.name = shop.getName();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
