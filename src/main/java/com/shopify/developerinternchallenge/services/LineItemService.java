package com.shopify.developerinternchallenge.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopify.developerinternchallenge.models.exceptions.ElementAlreadyExistException;
import com.shopify.developerinternchallenge.models.exceptions.InsufficientProductsAvailableException;
import com.shopify.developerinternchallenge.models.exceptions.NotFoundException;
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

	public LineItem getLineItemById(Long lineItemId) {
		if(lineItemId != null) {
			Optional<LineItem> lineItem = this.lineItemRepository.findById(lineItemId);
			if(lineItem.isPresent()) {
				return lineItem.get();
			}
		}
		return null;
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
		Product product = this.productService.getProductById(lineItem.getProduct().getId());
		if (product.getAvailableAmount() >= lineItem.getAmount()) {
			lineItem.setProduct(product);
			lineItem = this.lineItemRepository.save(lineItem);
			this.productService.addLineItem2Product(lineItem, product.getId());
			return lineItem;
		} else {
			throw new InsufficientProductsAvailableException(
					"Product [" + product.getId() + "] has just [" + product.getAvailableAmount()
							+ "] and you are trying to get [" + lineItem.getAmount() + "] products");
		}
	}

	@Transactional
	public void deleteLineItem(Long lineItemId) {
		if (!contains(lineItemId)) {
			throw new NotFoundException(lineItemId.toString());
		}
		LineItem lineItem = getLineItemById(lineItemId);
		this.lineItemRepository.delete(getLineItemById(lineItemId));
		this.productService.deleteLineItemFromProduct(lineItem, lineItem.getProduct().getId());
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
			throw new InsufficientProductsAvailableException("Product [" + product.getId() + "] has just ["
					+ product.getAvailableAmount() + "] and you are trying to get [" + newAmount + "] products");
		}
	}

	public boolean contains(LineItem lineItem) {
		return contains(lineItem.getId());
	}

	public boolean contains(Long lineItemId) {
		return getLineItemById(lineItemId) != null;
	}
}
