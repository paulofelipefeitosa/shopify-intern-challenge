package com.shopify.developerinternchallenge.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopify.developerinternchallenge.models.exceptions.ElementAlreadyExistException;
import com.shopify.developerinternchallenge.models.stock.Stock;
import com.shopify.developerinternchallenge.repositories.StockRepository;

@Service
public class StockService {

	@Autowired
	StockRepository stockRepository;

	public List<Stock> getAllStocks() {
		return this.stockRepository.findAll();
	}

	public Stock getStockById(String stockId) {
		return this.stockRepository.findById(stockId).get();
	}

	public Stock addStock(Stock stock) {
		if (contains(stock)) {
			throw new ElementAlreadyExistException(stock.toString());
		}
		return this.stockRepository.save(stock);
	}

	public void deleteStock(String stockId) {
		if (!contains(stockId)) {
			throw new NoSuchElementException(stockId);
		}
		this.stockRepository.delete(getStockById(stockId));
	}

	public boolean contains(Stock stock) {
		return contains(stock.getId());
	}

	public boolean contains(String stockId) {
		try {
			getStockById(stockId);
		} catch (RuntimeException e) {
			return false;
		}
		return true;
	}

}
