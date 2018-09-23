package com.shopify.developerinternchallenge.services;

import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopify.developerinternchallenge.models.exceptions.ElementAlreadyExistException;
import com.shopify.developerinternchallenge.models.exceptions.InsufficientProductsAvailable;
import com.shopify.developerinternchallenge.models.lineitems.LineItem;
import com.shopify.developerinternchallenge.models.product.Product;
import com.shopify.developerinternchallenge.repositories.LineItemRepository;

@Service
public class LineItemService {
	@Autowired
	LineItemRepository lineItemRepository;
	@Autowired
	ProductService productService;

	public List<LineItem> getAllLineItems() {
		return this.lineItemRepository.findAll();
	}

	public LineItem getLineItemById(String lineItemId) {
		return this.lineItemRepository.findById(lineItemId).get();
	}

	/**
	 * Add a LineItem. The operation is only performed if there are enough products
	 * to sell.
	 * 
	 * @param lineItem
	 * @return
	 */
	@Transactional
	public LineItem addLineItem(LineItem lineItem) {
		if (contains(lineItem)) {
			throw new ElementAlreadyExistException(lineItem.toString());
		}
		if (thereIsEnoughProductsAvailable(lineItem)) {
			return this.lineItemRepository.save(lineItem);
		} else {
			Product product = this.productService.getProductById(lineItem.getProduct().getId());
			throw new InsufficientProductsAvailable(
					"Product [" + product.getId() + "] has just [" + product.getAvailableAmount()
							+ "] and you are trying to get [" + lineItem.getAmount() + "] products");
		}
	}

	@Transactional
	public void deleteLineItem(String lineItemId) {
		if (!contains(lineItemId)) {
			throw new NoSuchElementException(lineItemId);
		}
		this.lineItemRepository.delete(getLineItemById(lineItemId));
	}

	@Transactional
	public LineItem editLineItemAmount(LineItem lineItem, Integer newAmount) {
		lineItem = getLineItemById(lineItem.getId());
		Product product = this.productService.getProductById(lineItem.getProduct().getId());
		Integer difference = newAmount - lineItem.getAmount();
		if (product.getAvailableAmount() >= difference) {
			lineItem.setAmount(newAmount);
			return this.lineItemRepository.saveAndFlush(lineItem);
		} else {
			throw new InsufficientProductsAvailable("Product [" + product.getId() + "] has just ["
					+ product.getAvailableAmount() + "] and you are trying to get [" + newAmount + "] products");
		}
	}

	public boolean thereIsEnoughProductsAvailable(LineItem lineItem) {
		Product product = this.productService.getProductById(lineItem.getProduct().getId());
		return product.getAvailableAmount() >= lineItem.getAmount();
	}

	public boolean contains(LineItem lineItem) {
		return contains(lineItem.getId());
	}

	public boolean contains(String lineItemId) {
		try {
			getLineItemById(lineItemId);
		} catch (RuntimeException e) {
			return false;
		}
		return true;
	}
}
