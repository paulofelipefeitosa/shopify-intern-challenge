package com.shopify.developerinternchallenge.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.shopify.developerinternchallenge.models.lineitems.LineItem;
import com.shopify.developerinternchallenge.models.order.Order;
import com.shopify.developerinternchallenge.models.product.Product;

@RestController
@CrossOrigin("*")
public class LineItemController {
	public static final String ENDPOINT = "/lineItems";

	@Autowired
	ProductController productController;
	@Autowired
	OrderController orderController;

	@RequestMapping(value = ShopController.ENDPOINT + "/{shopName}" + ProductController.ENDPOINT + "/{productId}"
			+ LineItemController.ENDPOINT, method = RequestMethod.GET)
	public @ResponseBody List<LineItem> getProductLineItems(@PathVariable String shopName,
			@PathVariable Long productId) {
		Product product = this.productController.getProduct(shopName, productId);
		return product.getLineItems();
	}

	@RequestMapping(value = ShopController.ENDPOINT + "/{shopName}" + ProductController.ENDPOINT + "/{productId}"
			+ LineItemController.ENDPOINT + "/{lineItemId}", method = RequestMethod.GET)
	public @ResponseBody LineItem getProductLineItem(@PathVariable String shopName, @PathVariable Long productId,
			@PathVariable Long lineItemId) {
		Product product = this.productController.getProduct(shopName, productId);
		return product.getLineItemWithId(lineItemId);
	}

	@RequestMapping(value = ShopController.ENDPOINT + "/{shopName}" + OrderController.ENDPOINT + "/{orderId}"
			+ LineItemController.ENDPOINT, method = RequestMethod.GET)
	public @ResponseBody List<LineItem> getOrderLineItems(@PathVariable String shopName, @PathVariable Long orderId) {
		Order order = this.orderController.getShopOrder(shopName, orderId);
		return order.getLineItems();
	}

	@RequestMapping(value = ShopController.ENDPOINT + "/{shopName}" + OrderController.ENDPOINT + "/{orderId}"
			+ LineItemController.ENDPOINT + "/{lineItemId}", method = RequestMethod.GET)
	public @ResponseBody LineItem getOrderLineItem(@PathVariable String shopName, @PathVariable Long orderId,
			@PathVariable Long lineItemId) {
		Order order = this.orderController.getShopOrder(shopName, orderId);
		return order.getLineItemWithId(lineItemId);
	}
}
