package com.shopify.developerinternchallenge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.shopify.developerinternchallenge.models.lineitems.LineItem;
import com.shopify.developerinternchallenge.models.order.Order;
import com.shopify.developerinternchallenge.models.product.Product;
import com.shopify.developerinternchallenge.models.shop.Shop;
import com.shopify.developerinternchallenge.models.stock.Stock;
import com.shopify.developerinternchallenge.services.LineItemService;
import com.shopify.developerinternchallenge.services.OrderService;
import com.shopify.developerinternchallenge.services.ProductService;
import com.shopify.developerinternchallenge.services.ShopService;
import com.shopify.developerinternchallenge.services.StockService;

@Component
public class DemoDataLoader implements ApplicationRunner {

	@Autowired
	ShopService shopService;
	@Autowired
	StockService stockService;
	@Autowired
	OrderService orderService;
	@Autowired
	ProductService productService;
	@Autowired
	LineItemService lineItemService;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Product product = new Product("FirstProduct", "This is the first product of the Shop", 10, 1.5);
		product = this.productService.addProduct(product);
		
		Stock stock = new Stock();
		stock.addProduct(product);
		stock = this.stockService.addStock(stock);
		
		LineItem lineItem = new LineItem(product, 5);
		lineItem = this.lineItemService.addLineItem(lineItem);
		
		Order order = new Order();
		order.addLineItem(lineItem);
		order = this.orderService.addOrder(order);
		
		Shop shop = new Shop("BoraBiexo", stock);
		shop.addOrder(order);
		shop = this.shopService.addShop(shop);
	}

}
