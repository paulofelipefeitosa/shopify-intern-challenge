package com.shopify.developerinternchallenge.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopify.developerinternchallenge.models.Shop;
import com.shopify.developerinternchallenge.repositories.ShopRepository;

@Service
public class ShopService {

	@Autowired
	ShopRepository shopRepository;
	
	public List<Shop> getAllShops() {
		return this.shopRepository.findAll();
	}

}
