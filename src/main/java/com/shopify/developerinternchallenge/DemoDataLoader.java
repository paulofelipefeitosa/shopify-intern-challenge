package com.shopify.developerinternchallenge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.shopify.developerinternchallenge.models.lineitems.LineItem;
import com.shopify.developerinternchallenge.models.order.Order;
import com.shopify.developerinternchallenge.models.product.Product;
import com.shopify.developerinternchallenge.models.shop.Shop;
import com.shopify.developerinternchallenge.services.ShopService;

@Component
public class DemoDataLoader implements ApplicationRunner {
	@Autowired
	ShopService shopService;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Shop shop = new Shop("BoraBiexo");
		shop = this.shopService.addShop(shop.getPublicShop());
		// System.out.println(shop);

		Product product = new Product("FirstProduct", "This is the first product of the Shop", 10, 1.5);
		product = this.shopService.addProduct2Shop(product.getPublicProduct(), shop);
		// System.out.println(product);
		// System.out.println(this.shopService.getShopByName(shop.getName()));

		LineItem lineItem = new LineItem(product, 5);
		Order order = new Order();
		order.addLineItem(lineItem);
		order = this.shopService.addOrder2Shop(order, shop.getPublicShop());
		// System.out.println(order);
	}

}
