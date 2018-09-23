package com.shopify.developerinternchallenge.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopify.developerinternchallenge.models.stock.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, String>{
	
}
