package com.shopify.developerinternchallenge.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.shopify.developerinternchallenge.models.exceptions.NotFoundException;
import com.shopify.developerinternchallenge.models.lineitems.LineItem;
import com.shopify.developerinternchallenge.models.order.Order;
import com.shopify.developerinternchallenge.models.product.Product;
import com.shopify.developerinternchallenge.services.LineItemService;
import com.shopify.developerinternchallenge.services.OrderService;

@RestController
@CrossOrigin("*")
public class LineItemController {
	public static final String ENDPOINT = "/lineItems";

	@Autowired
	ProductController productController;
	@Autowired
	OrderController orderController;
	@Autowired
	OrderService orderService;
	@Autowired
	LineItemService lineItemService;

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
		return getLineItemById(lineItemId, product);
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
		return getLineItemById(lineItemId, order);
	}

	@RequestMapping(value = ShopController.ENDPOINT + "/{shopName}" + OrderController.ENDPOINT + "/{orderId}"
			+ LineItemController.ENDPOINT, method = RequestMethod.POST)
	public @ResponseBody LineItem addOrderLineItem(@PathVariable String shopName, @PathVariable Long orderId,
			@RequestBody LineItem lineItem) {
		Order order = this.orderController.getShopOrder(shopName, orderId);
		return this.orderService.addLineItem2Order(lineItem, order);
	}

	@RequestMapping(value = ShopController.ENDPOINT + "/{shopName}" + OrderController.ENDPOINT + "/{orderId}"
			+ LineItemController.ENDPOINT + "/{lineItemId}", method = RequestMethod.DELETE)
	public @ResponseBody Boolean deleteOrderLineItem(@PathVariable String shopName, @PathVariable Long orderId,
			@PathVariable Long lineItemId) {
		Order order = this.orderController.getShopOrder(shopName, orderId);
		LineItem lineItem = getLineItemById(lineItemId, order);
		this.orderService.deleteLineItemFromOrder(lineItem, order);
		return true;
	}

	@RequestMapping(value = ShopController.ENDPOINT + "/{shopName}" + OrderController.ENDPOINT + "/{orderId}"
			+ LineItemController.ENDPOINT + "/{lineItemId}/amount/{newAmount}", method = RequestMethod.PATCH)
	public @ResponseBody LineItem editOrderLineItemProductAmount(@PathVariable String shopName,
			@PathVariable Long orderId, @PathVariable Long lineItemId, @PathVariable Integer newAmount) {
		Order order = this.orderController.getShopOrder(shopName, orderId);
		LineItem lineItem = getLineItemById(lineItemId, order);
		return this.lineItemService.editLineItemAmount(lineItem, newAmount);
	}

	private LineItem getLineItemById(Long lineItemId, Order order) {
		LineItem lineItem = order.getLineItemWithId(lineItemId);
		if (lineItem == null) {
			throw new NotFoundException(lineItemId.toString());
		}
		return lineItem;
	}

	private LineItem getLineItemById(Long lineItemId, Product product) {
		LineItem lineItem = product.getLineItemWithId(lineItemId);
		if (lineItem == null) {
			throw new NotFoundException(lineItemId.toString());
		}
		return lineItem;
	}

}
