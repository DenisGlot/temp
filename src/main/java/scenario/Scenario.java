package scenario;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import cache.Cache;
import cache.factory.CacheFacroty;
import cache.factory.CacheType;
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
 * In all operation I'm using class for cache, not dao because cache is already
 * using dao All caches use lazy initialization
 * 
 * @author Denis
 *
 */
public class Scenario {

	private final Logger logger = Logger.getLogger(Scenario.class);

	private UserCache userCache;

	private OrderCache orderCache;

	private OrderDetailsCache odCache;

	private ProductCache productCache;

	/**
	 * userCache can be null.In that case would be used userCache from Scenario
	 * class. It was done for performance in MailServlet
	 * @param user
	 * @param userCache
	 * @return
	 */
	public boolean registerUser(User user,UserCache userCache) {
		if(userCache == null) {
			userCache = this.userCache;
		}
		userCache = (UserCache) lazyInit(userCache, CacheType.USER);
		return userCache.save(user);
	}

	/**
	 * userCache can be null.In that case would be used userCache from Scenario
	 * class
	 * 
	 * @param user
	 * @param userCacheFrom
	 *            it was for AuthFilter where is already userCache
	 * @return
	 * @throws NullPointerException
	 *             if user == null
	 */
	public boolean authorization(User user, UserCache userCacheFrom) {
		if (user == null) {
			throw new NullPointerException();
		}
		if (userCacheFrom == null) {
			userCache = (UserCache) lazyInit(userCache, CacheType.USER);
			userCacheFrom = userCache;
		}
		return userCacheFrom.authorization(user);
	}

	public void putInBasket(ShoppingCard shoppingCard, Product product, Integer quantity) {
		shoppingCard.addProduct(product, quantity);
	}

	public void deleteFromBasket(ShoppingCard shoppingCard, Product product) {
		shoppingCard.removeProduct(product);
	}

	public void updateProductInBasket(ShoppingCard shoppingCard, Product product, Integer quantity) {
		shoppingCard.setQuantityOfProducts(product, quantity);
	}

	/**
	 * It works only with dao and cache yet The courier is first
	 * 
	 * @param shoppingCard
	 * @return true if order and orderDetails was saved in database
	 */
	public boolean buyFromBasket(ShoppingCard shoppingCard) {
		orderCache = (OrderCache) lazyInit(orderCache, CacheType.ORDER);
		odCache = (OrderDetailsCache) lazyInit(odCache, CacheType.ORDERDETAILS);
		productCache = (ProductCache) lazyInit(productCache, CacheType.PRODUCT);
		if (orderCache == null) {
			orderCache = new OrderCache(Order.class);
		}

		odCache = new OrderDetailsCache(OrderDetails.class);
		int orderid = -1;
		// The primary key will be created as new one in dao
		Order order = new Order(1, 1, shoppingCard.getUser().getId(), new Timestamp(new Date().getTime()), null);

		if (orderCache.save(order)) {
			orderid = OrderIdCounter.orderid.incrementAndGet();
			logger.debug(order + " was saved");
		} else {
			logger.error(order + "was not saved in database");
			return false;
		}
		for (Entry<Product, Integer> orderUnit : shoppingCard.getProducts().entrySet()) {
			OrderDetails od = new OrderDetails(1, orderid, shoppingCard.getUser().getId(),
					orderUnit.getKey().getPrice(), orderUnit.getValue(),
					(shoppingCard.getUser().getGroupid() == 1 ? new BigDecimal(0.2) : new BigDecimal(0)));
			if (odCache.save(od)) {
				logger.debug(od + "was saved in database");
			} else {
				logger.error(od + "was not saved in database");
				return false;
			}
			// Deleting from database quantity that was bought
			Product product = orderUnit.getKey();
			int quantity = product.getQuantity() - orderUnit.getValue();
			product.setQuantity(quantity<=0?0:quantity);
			// On case if quantity = 0 :) :)
//			product.setQuantity(100);
			productCache.update(product);
		}
		return true;

	}

	public List<Product> getCatalogByCategory(int categoryid) {
		productCache = (ProductCache) lazyInit(productCache, CacheType.PRODUCT);
		return productCache.getAllByCriteria("categoryid", categoryid);
	}

	/**
	 * saving time of sending to database and cache
	 * 
	 * @param courier
	 * @param order
	 */
	public void sendProdutsToClient(Courier courier, Order order) {
		orderCache = (OrderCache) lazyInit(orderCache, CacheType.ORDER);
		order.setShipperedDate(new Timestamp(new Date().getTime()));
		orderCache.save(order);
	}

	public void sendFeedBack(String message,String subject,String email) {
		new Sender().sendFeedBack(message,subject,email);
	}

	/**
	 * Lazy initialization for any cache
	 * 
	 * @param cache
	 * @param type
	 *            Class of entity in cache
	 */
	public Cache lazyInit(Cache cache, CacheType type) {
		if (cache == null) {
			return new CacheFacroty().getCache(type);
		}
		return cache;

	}

}
