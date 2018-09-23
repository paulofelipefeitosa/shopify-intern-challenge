package com.shopify.developerinternchallenge.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopify.developerinternchallenge.models.exceptions.ElementAlreadyExistException;
import com.shopify.developerinternchallenge.models.exceptions.NotFoundException;
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

	public Shop getShopByName(String shopName) {
		if (shopName != null) {
			Optional<Shop> shop = this.shopRepository.findById(shopName);
			if (shop.isPresent()) {
				return shop.get();
			}
		}
		return null;
	}

	public Shop addShop(PublicShop shop) {
		return addShop(new Shop(shop.getName(), new Stock()));
	}

	@Transactional
	private Shop addShop(Shop shop) {
		if (contains(shop)) {
			throw new ElementAlreadyExistException(shop.toString());
		}
		Stock stock = this.stockService.addStock(shop.getStock());
		shop.setStock(stock);
		return this.shopRepository.save(shop);
	}

	@Transactional
	public Order addOrderToShop(Order order, PublicShop publicShop) {
		order = this.orderService.addOrder(order);
		Shop shop = getShopByName(publicShop.getName());
		shop.addOrder(order);
		this.shopRepository.saveAndFlush(shop);
		return order;
	}

	@Transactional
	public void deleteShop(String shopId) {
		if (!contains(shopId)) {
			throw new NotFoundException(shopId);
		}
		Shop shop = getShopByName(shopId);
		Stock stock = shop.getStock();
		this.stockService.deleteStock(stock.getId());
		for (Order order : shop.getOrders().values()) {
			this.orderService.deleteOrder(order.getId());
		}
		this.shopRepository.delete(shop);
	}

	@Transactional
	public Shop deleteShopOrder(Order order, PublicShop publicShop) {
		this.orderService.deleteOrder(order.getId());
		Shop shop = getShopByName(publicShop.getName());
		shop.deleteOrder(order.getId());
		return this.shopRepository.saveAndFlush(shop);
	}

	public boolean contains(Shop shop) {
		return contains(shop.getName());
	}

	public boolean contains(String shopId) {
		return getShopByName(shopId) != null;
	}

}
