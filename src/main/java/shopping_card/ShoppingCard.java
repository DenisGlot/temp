package shopping_card;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

import dao.entity.Product;
import dao.entity.User;

public class ShoppingCard {
	
	private final Logger logger = Logger.getLogger(ShoppingCard.class);

	private User user;
	
	// Amount  of products in this shopping card
	private AtomicInteger amount;
	
	private ConcurrentHashMap<Product, Integer> products;
	
	public ShoppingCard(User user) {
		this.user = user;
		products = new ConcurrentHashMap<>();
		amount = new AtomicInteger(0);
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public ConcurrentHashMap<Product, Integer> getProducts() {
		return products;
	}

	/**
	 * Method put() in ConcurrentHashMap is working like replace
	 * So be careful if user wants to put in basket two same products
	 * @param product
	 * @param quantity
	 */
	public void addProduct(Product product, Integer quantity) {
		products.put(product, quantity);
		amount.incrementAndGet();
	}
	
	public void setQuantityOfProduct(Product product,Integer quantity) {
		if(products.replace(product, quantity)==null) {
			logger.warn("In method setQuantityOfProducts was not any replacement!!!");
		}
	}
	
	public void removeProduct(Product product) {
		products.remove(product);
		amount.decrementAndGet();
	}
	
	public void removeAllProducts() {
		products.clear();
	}
	
	public int size() {
		return amount.get();
	}
	
	@Override
	public String toString() {
		return "ShoppingCard [user=" + user + "]";
	}

}
