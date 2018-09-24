package com.shopify.developerinternchallenge.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopify.developerinternchallenge.models.exceptions.ElementAlreadyExistException;
import com.shopify.developerinternchallenge.models.exceptions.NotFoundException;
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
		if (productId != null) {
			Optional<Product> product = this.productRepository.findById(productId);
			if (product.isPresent()) {
				return product.get();
			}
		}
		return null;
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
			throw new NotFoundException(productId);
		}
		this.productRepository.delete(getProductById(productId));
	}

	@Transactional
	public Product editProductName(Product product, String newName) {
		product.setName(newName);
		return this.productRepository.saveAndFlush(product);
	}

	@Transactional
	public Product editProductDescription(Product product, String newDescription) {
		product.setDescription(newDescription);
		return this.productRepository.saveAndFlush(product);
	}

	@Transactional
	public Product editProductPrice(Product product, Double newPrice) {
		product.setPrice(newPrice);
		return this.productRepository.saveAndFlush(product);
	}

	@Transactional
	public Product addProduct2Stock(Product product, Integer add2Stock) {
		product.add2Stock(add2Stock);
		return this.productRepository.saveAndFlush(product);
	}

	public boolean contains(Product product) {
		return contains(product.getId());
	}

	public boolean contains(String productId) {
		return getProductById(productId) != null;
	}
}
