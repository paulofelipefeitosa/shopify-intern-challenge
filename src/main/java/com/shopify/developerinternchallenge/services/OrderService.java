package com.shopify.developerinternchallenge.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopify.developerinternchallenge.models.Order;
import com.shopify.developerinternchallenge.models.exceptions.ElementAlreadyExistException;
import com.shopify.developerinternchallenge.repositories.OrderRepository;

@Service
public class OrderService {

	@Autowired
	OrderRepository orderRepository;
	
	public List<Order> getAllOrders() {
		return this.orderRepository.findAll();
	}

	public Order getOrderById(String orderId) {
		return this.orderRepository.findById(orderId).get();
	}

	public Order addOrder(Order order) {
		if (contains(order)) {
			throw new ElementAlreadyExistException(order.toString());
		}
		return this.orderRepository.save(order);
	}

	public void deleteOrder(String orderId) {
		if (!contains(orderId)) {
			throw new NoSuchElementException(orderId);
		}
		this.orderRepository.delete(getOrderById(orderId));
	}

	public boolean contains(Order order) {
		return contains(order.getId());
	}

	public boolean contains(String orderId) {
		try {
			getOrderById(orderId);
		} catch (RuntimeException e) {
			return false;
		}
		return true;
	}
}
