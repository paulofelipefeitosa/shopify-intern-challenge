package com.shopify.developerinternchallenge.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopify.developerinternchallenge.models.lineitems.LineItem;

@Repository
public interface LineItemRepository extends JpaRepository<LineItem, Long> {

}
