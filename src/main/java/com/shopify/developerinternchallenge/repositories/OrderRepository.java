package com.shopify.developerinternchallenge.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopify.developerinternchallenge.models.order.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

}
