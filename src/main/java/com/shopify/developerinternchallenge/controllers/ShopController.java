package com.shopify.developerinternchallenge.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.shopify.developerinternchallenge.models.shop.PublicShop;
import com.shopify.developerinternchallenge.services.ShopService;

@RestController
@CrossOrigin("*")
@RequestMapping("/shops")
public class ShopController {
	
	@Autowired
	ShopService shopService;
	
	@RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<PublicShop> getShops() {
		//@formatter:off
        return this.shopService.getAllShops().stream()
        		.map(e -> e.getPublicShop())
        		.collect(Collectors.toList());
        //@formatter:on
    }
	
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody PublicShop addShop(@RequestBody PublicShop shop) {
        return this.shopService.addShop(shop).getPublicShop();
    }
	
}
