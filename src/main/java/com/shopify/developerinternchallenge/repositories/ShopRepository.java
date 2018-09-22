package com.shopify.developerinternchallenge.repositories;

import org.springframework.stereotype.Repository;

import com.shopify.developerinternchallenge.models.Shop;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ShopRepository extends JpaRepository<Shop, String> {

}
