package com.shopify.developerinternchallenge.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopify.developerinternchallenge.models.exceptions.ElementAlreadyExistException;
import com.shopify.developerinternchallenge.models.lineitems.LineItem;
import com.shopify.developerinternchallenge.repositories.LineItemRepository;

@Service
public class LineItemService {

	@Autowired
	LineItemRepository lineItemRepository;
	
	public List<LineItem> getAllLineItems() {
		return this.lineItemRepository.findAll();
	}

	public LineItem getLineItemById(String lineItemId) {
		return this.lineItemRepository.findById(lineItemId).get();
	}

	public LineItem addLineItem(LineItem lineItem) {
		if (contains(lineItem)) {
			throw new ElementAlreadyExistException(lineItem.toString());
		}
		return this.lineItemRepository.save(lineItem);
	}

	public void deleteLineItem(String lineItemId) {
		if (!contains(lineItemId)) {
			throw new NoSuchElementException(lineItemId);
		}
		this.lineItemRepository.delete(getLineItemById(lineItemId));
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
