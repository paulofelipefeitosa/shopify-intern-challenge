package com.shopify.developerinternchallenge.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopify.developerinternchallenge.models.Product;
import com.shopify.developerinternchallenge.models.exceptions.ElementAlreadyExistException;
import com.shopify.developerinternchallenge.repositories.ProductRepository;

@Service
public class ProductService {
	@Autowired
	ProductRepository productRepository;
	
	public List<Product> getAllProducts() {
		return this.productRepository.findAll();
	}

	public Product getProductById(String productId) {
		return this.productRepository.findById(productId).get();
	}

	public Product addProduct(Product product) {
		if (contains(product)) {
			throw new ElementAlreadyExistException(product.toString());
		}
		return this.productRepository.save(product);
	}

	public void deleteProduct(String productId) {
		if (!contains(productId)) {
			throw new NoSuchElementException(productId);
		}
		this.productRepository.delete(getProductById(productId));
	}

	public boolean contains(Product product) {
		return contains(product.getId());
	}

	public boolean contains(String productId) {
		try {
			getProductById(productId);
		} catch (RuntimeException e) {
			return false;
		}
		return true;
	}
}
