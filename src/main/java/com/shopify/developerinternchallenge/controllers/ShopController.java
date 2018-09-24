package com.shopify.developerinternchallenge.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.shopify.developerinternchallenge.models.exceptions.NotFoundException;
import com.shopify.developerinternchallenge.models.product.PublicProduct;
import com.shopify.developerinternchallenge.models.shop.PublicShop;
import com.shopify.developerinternchallenge.models.shop.Shop;
import com.shopify.developerinternchallenge.services.ShopService;

@RestController
@CrossOrigin("*")
@RequestMapping(ShopController.ENDPOINT)
public class ShopController {
	public static final String ENDPOINT = "/shops";
	
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
	
	@RequestMapping(value = "/{shopName}", method = RequestMethod.GET)
	public @ResponseBody Shop getShop(@PathVariable String shopName) {
        Shop shop = getShopByName(shopName);
        return shop;
    }

	@RequestMapping(value = "/{shopName}" + ProductController.ENDPOINT, method = RequestMethod.GET)
	public @ResponseBody List<PublicProduct> getShopProducts(@PathVariable String shopName) {
		Shop shop = getShopByName(shopName);
        //@formatter:off
        return shop.getProducts().values().stream()
        		.map(e -> e.getPublicProduct())
        		.collect(Collectors.toList());
        //@formatter:on
    }
	
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody PublicShop addShop(@RequestBody PublicShop shop) {
        return this.shopService.addShop(shop).getPublicShop();
    }
	
	@RequestMapping(value = "/{shopName}", method = RequestMethod.DELETE)
	public @ResponseBody Boolean deleteShop(@PathVariable String shopId) {
        this.shopService.deleteShop(shopId);
        return true;
    }
	
	private Shop getShopByName(String shopName) {
		Shop shop = this.shopService.getShopByName(shopName);
        if(shop == null) {
        	throw new NotFoundException(shopName);
        }
		return shop;
	}
	
}
