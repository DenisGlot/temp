package scenario;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import cache.Cache;
import cache.realization.UserCache;
import cache.realization.simple.CourierCache;
import cache.realization.simple.OrderCache;
import cache.realization.simple.OrderDetailsCache;
import cache.realization.simple.ProductCache;
import dao.entity.Category;
import dao.entity.Courier;
import dao.entity.Order;
import dao.entity.OrderDetails;
import dao.entity.Product;
import dao.entity.User;
import hash.Hashing;
import id_counter.OrderIdCounter;
import mail.send.Sender;
import shopping_card.ShoppingCard;

/**
 * In all operation I'm using class for cache, not dao
 * because cache is already using dao
 * @author Denis
 *
 */
public class Scenario {
	
	private final Logger logger = Logger.getLogger(Scenario.class);
	
	private UserCache userCache;
	
	private OrderCache orderCache;
	
	private OrderDetailsCache odCache;
	
	private ProductCache productCache;
	
	private CourierCache courierCache;
	
	public Scenario() {
		userCache = new UserCache(User.class);
		orderCache = new OrderCache(Order.class);
		odCache = new OrderDetailsCache(OrderDetails.class);
		productCache = new ProductCache(Product.class);
		courierCache = new CourierCache(Courier.class);
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
	
	public void deleteFromBasket(ShoppingCard shoppingCard, Product product) {
		shoppingCard.removeProduct(product);
	}
	
	public void updateProductInBasket(ShoppingCard shoppingCard,Product product, Integer quantity) {
		shoppingCard.setQuantityOfProducts(product, quantity);
	}
	
	
	/**
	 * It works only with dao and cache yet
	 * @param shoppingCard
	 * @return true if order and orderDetails was saved in database
 	 */
	public boolean buyFromBasket(ShoppingCard shoppingCard) {
		int orderid = -1;
		//The primary key will be created as new one in dao
		Order order = new Order(1,shoppingCard.getUser().getId(),new Timestamp(new Date().getTime()),null);
		
		if(orderCache.save(order)) {
			orderid=OrderIdCounter.orderid.incrementAndGet();
			logger.debug(order + " was saved");
		} else {
			logger.error(order + "was not saved in database");
	        return false;
		}
		for (Entry<Product, Integer> orderUnit : shoppingCard.getProducts().entrySet()) {
			OrderDetails od = new OrderDetails(1, orderid, shoppingCard.getUser().getId(),
					orderUnit.getKey().getPrice(), orderUnit.getValue(), (shoppingCard.getUser().getGroupid()==1?new BigDecimal(0.2):new BigDecimal(0)));
			if(odCache.save(od)) {
				logger.debug(od + "was saved in database");
			} else {
				logger.error(od + "was not saved in database");
				return false;
			}
		}
		return true;
		
	}
	
	
	public List<Product> getCatalogByCategory(Category category) {
        return productCache.getAllByCriteria("categoryid", category.getCategoryid());
	}
	
	/**
	 * saving time of sending to database and cache
	 * @param courier
	 * @param order
	 */
	public void sendProdutsToClient(Courier courier, Order order) {
		order.setShipperedDate(new Timestamp(new Date().getTime()));
	    orderCache.save(order);
	}
	
	public void sendFeedBack(String message) {
		new Sender().sendFeedBack(message);
	}

}
