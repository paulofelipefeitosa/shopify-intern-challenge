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
		Shop shop = new Shop("BoraBiexo", new Stock());
		shop = this.shopService.addShop(shop.getPublicShop());
		
		System.out.println(shop);
		
		Product product = new Product("FirstProduct", "This is the first product of the Shop", 10, 1.5);
		Stock stock = shop.getStock();
		System.out.println(stock);
		System.out.println(stock.getProducts());
		product = this.stockService.addProductToStock(product.getPublicProduct(), stock);
		System.out.println(product);
		System.out.println(this.stockService.getStockById(stock.getId()));
		
		LineItem lineItem = new LineItem(product, 5);
		Order order = new Order();
		order.addLineItem(lineItem);
		order = this.shopService.addOrderToShop(order, shop.getPublicShop());
		System.out.println(order);
	}

}
