package com.shopify.developerinternchallenge.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopify.developerinternchallenge.models.exceptions.ElementAlreadyExistException;
import com.shopify.developerinternchallenge.models.exceptions.InsufficientProductsAvailableException;
import com.shopify.developerinternchallenge.models.exceptions.NotFoundException;
import com.shopify.developerinternchallenge.models.lineitems.LineItem;
import com.shopify.developerinternchallenge.models.order.Order;
import com.shopify.developerinternchallenge.repositories.OrderRepository;

@Service
public class OrderService {
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	LineItemService lineItemService;

	public List<Order> getAllOrders() {
		return this.orderRepository.findAll();
	}

	public Order getOrderById(String orderId) {
		if (orderId != null) {
			Optional<Order> order = this.orderRepository.findById(orderId);
			if (order.isPresent()) {
				return order.get();
			}
		}
		return null;
	}

	/**
	 * 
	 * @param order
	 * @return
	 */
	@Transactional
	public Order addOrder(Order order) {
		if (contains(order)) {
			throw new ElementAlreadyExistException(order.toString());
		}
		for (LineItem lineItem : order.getLineItems()) {
			if (!this.lineItemService.thereIsEnoughProductsAvailable(lineItem)) {
				throw new InsufficientProductsAvailableException("Cannot process " + order.toString()
						+ " there are not enough products available for " + lineItem.toString());
			}
		}
		List<LineItem> newLineItems = new ArrayList<>();
		for (LineItem lineItem : order.getLineItems()) {
			lineItem = this.lineItemService.addLineItem(lineItem);
			newLineItems.add(lineItem);
		}
		order.setLineItems(newLineItems);
		return this.orderRepository.save(order);
	}

	@Transactional
	public LineItem addLineItem2Order(LineItem lineItem, Order order) {
		lineItem = this.lineItemService.addLineItem(lineItem);
		order.addLineItem(lineItem);
		this.orderRepository.saveAndFlush(order);
		return lineItem;
	}

	@Transactional
	public void deleteOrder(String orderId) {
		if (!contains(orderId)) {
			throw new NotFoundException(orderId);
		}
		Order order = getOrderById(orderId);
		for (LineItem lineItem : order.getLineItems()) {
			this.lineItemService.deleteLineItem(lineItem.getId());
		}
		this.orderRepository.delete(order);
	}

	@Transactional
	public Order deleteLineItemFromOrder(LineItem lineItem, Order order) {
		this.lineItemService.deleteLineItem(lineItem.getId());
		order.deleteLineItemWithId(lineItem.getId());
		return this.orderRepository.saveAndFlush(order);
	}

	public boolean contains(Order order) {
		return contains(order.getId());
	}

	public boolean contains(String orderId) {
		return getOrderById(orderId) != null;
	}
}
