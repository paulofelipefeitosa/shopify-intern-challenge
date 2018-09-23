package com.shopify.developerinternchallenge.services;

import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopify.developerinternchallenge.models.exceptions.ElementAlreadyExistException;
import com.shopify.developerinternchallenge.models.order.Order;
import com.shopify.developerinternchallenge.models.shop.PublicShop;
import com.shopify.developerinternchallenge.models.shop.Shop;
import com.shopify.developerinternchallenge.models.stock.Stock;
import com.shopify.developerinternchallenge.repositories.ShopRepository;

@Service
public class ShopService {

	@Autowired
	ShopRepository shopRepository;
	@Autowired
	StockService stockService;
	@Autowired
	OrderService orderService;

	public List<Shop> getAllShops() {
		return this.shopRepository.findAll();
	}

	public Shop getShopName(String shopName) {
		return this.shopRepository.findById(shopName).get();
	}
	
	public Shop addShop(PublicShop shop) {
		return addShop(new Shop(shop.getName(), new Stock()));
	}

	@Transactional
	public Shop addShop(Shop shop) {
		if (contains(shop)) {
			throw new ElementAlreadyExistException(shop.toString());
		}
		shop.setStock(this.stockService.addStock(shop.getStock()));
		return this.shopRepository.save(shop);
	}
	
	@Transactional
	public Order addOrderToShop(Order order, PublicShop publicShop) {
		order = this.orderService.addOrder(order);
		Shop shop = getShopName(publicShop.getName()); 
		shop.addOrder(order);
		this.shopRepository.saveAndFlush(shop);
		return order;
	}
	
	@Transactional
	public void deleteShop(String shopId) {
		if (!contains(shopId)) {
			throw new NoSuchElementException(shopId);
		}
		Shop shop = getShopName(shopId);
		Stock stock = shop.getStock();
		this.stockService.deleteStock(stock.getId());
		for(Order order : shop.getOrders().values()) {
			this.orderService.deleteOrder(order.getId());
		}
		this.shopRepository.delete(shop);
	}
	
	@Transactional
	public void deleteShopOrder(Order order, PublicShop publicShop) {
		this.orderService.deleteOrder(order.getId());
		Shop shop = getShopName(publicShop.getName()); 
		shop.deleteOrder(order.getId());
		this.shopRepository.saveAndFlush(shop);
	}
	
	public boolean contains(Shop shop) {
		return contains(shop.getName());
	}

	public boolean contains(String shopId) {
		try {
			getShopName(shopId);
		} catch (RuntimeException e) {
			return false;
		}
		return true;
	}

}
