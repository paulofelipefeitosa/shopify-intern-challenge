package com.shopify.developerinternchallenge.services;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopify.developerinternchallenge.models.exceptions.ElementAlreadyExistException;
import com.shopify.developerinternchallenge.models.product.Product;
import com.shopify.developerinternchallenge.models.product.PublicProduct;
import com.shopify.developerinternchallenge.models.stock.Stock;
import com.shopify.developerinternchallenge.repositories.StockRepository;

@Service
public class StockService {
	@Autowired
	StockRepository stockRepository;
	@Autowired
	ProductService productService;

	public List<Stock> getAllStocks() {
		return this.stockRepository.findAll();
	}

	public Stock getStockById(String stockId) {
		if (stockId != null) {
			Optional<Stock> stock = this.stockRepository.findById(stockId);
			if (stock.isPresent()) {
				return stock.get();
			}
		}
		return null;
	}

	@Transactional(dontRollbackOn = RuntimeException.class)
	public Stock addStock(Stock stock) {
		if (contains(stock)) {
			throw new ElementAlreadyExistException(stock.toString());
		}
		if (stock.getProducts() == null) {
			stock.setProducts(new HashMap<String, Product>());
		}
		for (Product product : stock.getProducts().values()) {
			if (product != null) {
				product = this.productService.addProduct(product.getPublicProduct());
				stock.addProduct(product);
			}
		}
		return this.stockRepository.save(stock);
	}

	@Transactional
	public Product addProductToStock(PublicProduct publicProduct, Stock stock) {
		Product product = this.productService.addProduct(publicProduct);
		stock.addProduct(product);
		this.stockRepository.saveAndFlush(stock);
		return product;
	}

	@Transactional
	public void deleteStock(String stockId) {
		if (!contains(stockId)) {
			throw new NoSuchElementException(stockId);
		}
		Stock stock = getStockById(stockId);
		for (Product product : stock.getProducts().values()) {
			this.productService.deleteProduct(product.getId());
		}
		this.stockRepository.delete(stock);
	}

	@Transactional
	public Stock deleteStockProduct(Product product, Stock stock) {
		this.productService.deleteProduct(product.getId());
		stock.deleteProduct(product);
		return this.stockRepository.saveAndFlush(stock);
	}

	public boolean contains(Stock stock) {
		return contains(stock.getId());
	}

	public boolean contains(String stockId) {
		return getStockById(stockId) != null;
	}

}
