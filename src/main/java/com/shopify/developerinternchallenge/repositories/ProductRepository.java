package com.shopify.developerinternchallenge.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopify.developerinternchallenge.models.product.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
