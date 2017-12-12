package scenario;

import java.sql.Timestamp;
import java.util.Date;

import cache.Cache;
import cache.realization.UserCache;
import cache.realization.simple.OrderCache;
import dao.entity.Order;
import dao.entity.OrderDetails;
import dao.entity.Product;
import dao.entity.User;
import hash.Hashing;
import id_counter.OrderIdCounter;
import shopping_card.ShoppingCard;

/**
 * In all operation I'm using class for cache, not dao
 * because cache is already using dao
 * @author Denis
 *
 */
public class Scenario {
	
	public UserCache userCache;
	
	public OrderCache orderCache;
	
	public Scenario() {
		userCache = new UserCache(User.class);
		orderCache = new OrderCache(Order.class);
	}
	
	public boolean registerUser(User user) {
		return userCache.save(user);
	}
	
	public boolean authorization(User user) {
		if(userCache.get(user.getEmail()) == null) {
			//The email was not neither in cache nor in database
			return false;
		} else {
			return userCache.authorization(user);
		}
	}
	
	public void putInBasket(ShoppingCard shoppingCard,Product product, Integer quantity) {
		shoppingCard.addProduct(product, quantity);
	}
	
	/**
	 * I use time to find the id from 
	 * @param shoppingCard
	 */
	public void buyFromBasket(ShoppingCard shoppingCard) {
		int orderid;
		Timestamp time = new Timestamp(new Date().getTime());
		//The primary key will be created as new one in dao
		Order order = new Order(1,shoppingCard.getUser().getId(),time,null);
		
		if(orderCache.save(order)) {
			orderid=OrderIdCounter.orderid.incrementAndGet();
		}
		//TODO I want to sleep zZZZZZZ
//		for(Product product : shoppingCard.getProducts().)
//		OrderDetails od = new OrderDetails(1, orderid, shoppingCard., price, quantity, discount)
		
		
	}

}
