package com.shopify.developerinternchallenge.services;

import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopify.developerinternchallenge.models.exceptions.ElementAlreadyExistException;
import com.shopify.developerinternchallenge.models.product.Product;
import com.shopify.developerinternchallenge.models.product.PublicProduct;
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

	@Transactional
	public Product addProduct(PublicProduct publicProduct) {
		Product product = new Product(publicProduct.getName(), publicProduct.getDescription(),
				publicProduct.getAvailableAmount(), publicProduct.getPrice());
		if (contains(product)) {
			throw new ElementAlreadyExistException(product.toString());
		}
		return this.productRepository.save(product);
	}

	@Transactional
	public void deleteProduct(String productId) {
		if (!contains(productId)) {
			throw new NoSuchElementException(productId);
		}
		this.productRepository.delete(getProductById(productId));
	}
	
	@Transactional
	public Product editProductName(String productId, String newName) {
		Product product = getProductById(productId);
		product.setName(newName);
		return this.productRepository.saveAndFlush(product);
	}
	
	@Transactional
	public Product editProductDescription(String productId, String newDescription) {
		Product product = getProductById(productId);
		product.setDescription(newDescription);
		return this.productRepository.saveAndFlush(product);
	}
	
	@Transactional
	public Product editProductPrice(String productId, Double newPrice) {
		Product product = getProductById(productId);
		product.setPrice(newPrice);
		return this.productRepository.saveAndFlush(product);
	}
	
	@Transactional
	public Product addProduct2Stock(String productId, Integer add2Stock) {
		Product product = getProductById(productId);
		product.add2Stock(add2Stock);
		return this.productRepository.saveAndFlush(product);
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
