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
import com.shopify.developerinternchallenge.models.product.Product;
import com.shopify.developerinternchallenge.models.product.PublicProduct;
import com.shopify.developerinternchallenge.models.shop.Shop;
import com.shopify.developerinternchallenge.services.ProductService;
import com.shopify.developerinternchallenge.services.ShopService;

@RestController
@CrossOrigin("*")
public class ProductController {
	public static final String ENDPOINT = "/products";

	@Autowired
	ShopController shopController;
	@Autowired
	ShopService shopService;
	@Autowired
	ProductService productService;

	@RequestMapping(value = "/{shopName}" + ProductController.ENDPOINT, method = RequestMethod.GET)
	public @ResponseBody List<PublicProduct> getShopProducts(@PathVariable String shopName) {
		Shop shop = this.shopController.getShop(shopName); 
        //@formatter:off
        return shop.getProducts().values().stream()
        		.map(e -> e.publicProduct())
        		.collect(Collectors.toList());
        //@formatter:on
    }
	
	@RequestMapping(value = ShopController.ENDPOINT + "/{shopName}" + ProductController.ENDPOINT
			+ "/{productId}", method = RequestMethod.GET)
	public @ResponseBody Product getProduct(@PathVariable String shopName, @PathVariable Long productId) {
		Shop shop = this.shopController.getShop(shopName);
		return getProductById(productId, shop);
	}
	
	@RequestMapping(value = ShopController.ENDPOINT + "/{shopName}"
			+ ProductController.ENDPOINT, method = RequestMethod.POST)
	public @ResponseBody PublicProduct addShop(@PathVariable String shopName,
			@RequestBody PublicProduct publicProduct) {
		Shop shop = this.shopController.getShop(shopName);
		return this.shopService.addProduct2Shop(publicProduct, shop).publicProduct();
	}

	@RequestMapping(value = ShopController.ENDPOINT + "/{shopName}" + ProductController.ENDPOINT
			+ "/{productId}", method = RequestMethod.DELETE)
	public @ResponseBody Boolean deleteShop(@PathVariable String shopName, @PathVariable Long productId) {
		Shop shop = this.shopController.getShop(shopName);
		Product product = getProductById(productId, shop);
		this.shopService.deleteShopProduct(product, shop);
		return true;
	}

	@RequestMapping(value = ShopController.ENDPOINT + "/{shopName}" + ProductController.ENDPOINT
			+ "/{productId}/name/{name}", method = RequestMethod.PATCH)
	public @ResponseBody PublicProduct editProductName(@PathVariable String shopName, @PathVariable Long productId,
			@PathVariable String name) {
		Shop shop = this.shopController.getShop(shopName);
		Product product = getProductById(productId, shop);
		return this.productService.editProductName(product, name).publicProduct();
	}

	@RequestMapping(value = ShopController.ENDPOINT + "/{shopName}" + ProductController.ENDPOINT
			+ "/{productId}/description/{description}", method = RequestMethod.PATCH)
	public @ResponseBody PublicProduct editProductDescription(@PathVariable String shopName,
			@PathVariable Long productId, @PathVariable String description) {
		Shop shop = this.shopController.getShop(shopName);
		Product product = getProductById(productId, shop);
		return this.productService.editProductDescription(product, description).publicProduct();
	}

	@RequestMapping(value = ShopController.ENDPOINT + "/{shopName}" + ProductController.ENDPOINT
			+ "/{productId}/price/{price}", method = RequestMethod.PATCH)
	public @ResponseBody PublicProduct editProductDescription(@PathVariable String shopName,
			@PathVariable Long productId, @PathVariable Double price) {
		Shop shop = this.shopController.getShop(shopName);
		Product product = getProductById(productId, shop);
		return this.productService.editProductPrice(product, price).publicProduct();
	}

	@RequestMapping(value = ShopController.ENDPOINT + "/{shopName}" + ProductController.ENDPOINT
			+ "/{productId}/addStock/{add2Stock}", method = RequestMethod.PATCH)
	public @ResponseBody PublicProduct addProduct2Stock(@PathVariable String shopName, @PathVariable Long productId,
			@PathVariable Integer add2Stock) {
		Shop shop = this.shopController.getShop(shopName);
		Product product = getProductById(productId, shop);
		return this.productService.addProduct2Stock(product, add2Stock).publicProduct();
	}

	private Product getProductById(Long productId, Shop shop) {
		Product product = shop.getProductsById(productId);
		if (product == null) {
			throw new NotFoundException(productId.toString());
		}
		return product;
	}
}
