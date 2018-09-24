package com.shopify.developerinternchallenge.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.shopify.developerinternchallenge.models.exceptions.NotFoundException;
import com.shopify.developerinternchallenge.models.order.Order;
import com.shopify.developerinternchallenge.models.shop.Shop;
import com.shopify.developerinternchallenge.services.ShopService;

@RestController
@CrossOrigin("*")
public class OrderController {
	public static final String ENDPOINT = "/orders";

	@Autowired
	ShopController shopController;
	@Autowired
	ShopService shopService;

	@RequestMapping(value = "/{shopName}" + OrderController.ENDPOINT, method = RequestMethod.GET)
	public @ResponseBody Collection<Order> getShopOrders(@PathVariable String shopName) {
		Shop shop = this.shopController.getShop(shopName);
		return shop.getOrders().values();
	}

	@RequestMapping(value = ShopController.ENDPOINT + "/{shopName}" + OrderController.ENDPOINT
			+ "/{orderId}", method = RequestMethod.GET)
	public @ResponseBody Order getShopOrder(@PathVariable String shopName, @PathVariable Long orderId) {
		Shop shop = this.shopController.getShop(shopName);
		return getOrderById(orderId, shop);
	}

	@RequestMapping(value = ShopController.ENDPOINT + "/{shopName}"
			+ OrderController.ENDPOINT, method = RequestMethod.POST)
	public @ResponseBody Order addShopOrder(@PathVariable String shopName, @RequestBody Order order) {
		Shop shop = this.shopController.getShop(shopName);
		return this.shopService.addOrder2Shop(order, shop.getPublicShop());
	}

	@RequestMapping(value = ShopController.ENDPOINT + "/{shopName}" + OrderController.ENDPOINT
			+ "/{orderId}", method = RequestMethod.DELETE)
	public @ResponseBody Boolean deleteShopOrder(@PathVariable String shopName, @PathVariable Long orderId) {
		Shop shop = this.shopController.getShop(shopName);
		Order order = getOrderById(orderId, shop);
		this.shopService.deleteShopOrder(order, shop.getPublicShop());
		return true;
	}

	private Order getOrderById(Long orderId, Shop shop) {
		Order order = shop.getOrderById(orderId);
		if (order == null) {
			throw new NotFoundException(orderId.toString());
		}
		return order;
	}

}
