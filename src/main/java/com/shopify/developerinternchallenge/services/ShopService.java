package com.shopify.developerinternchallenge.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopify.developerinternchallenge.models.Shop;
import com.shopify.developerinternchallenge.models.exceptions.ElementAlreadyExistException;
import com.shopify.developerinternchallenge.repositories.ShopRepository;

@Service
public class ShopService {

	@Autowired
	ShopRepository shopRepository;

	public List<Shop> getAllShops() {
		return this.shopRepository.findAll();
	}

	public Shop getShopById(String shopId) {
		return this.shopRepository.findById(shopId).get();
	}

	public Shop addShop(Shop shop) {
		if (contains(shop)) {
			throw new ElementAlreadyExistException(shop.toString());
		}
		return this.shopRepository.save(shop);
	}

	public void deleteShop(String shopId) {
		if (!contains(shopId)) {
			throw new NoSuchElementException(shopId);
		}
		this.shopRepository.delete(getShopById(shopId));
	}

	public boolean contains(Shop shop) {
		return contains(shop.getId());
	}

	public boolean contains(String shopId) {
		try {
			getShopById(shopId);
		} catch (RuntimeException e) {
			return false;
		}
		return true;
	}

}
