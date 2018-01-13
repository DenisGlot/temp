package scenario;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import cache.Cache;
import cache.factory.CacheFacroty;
import cache.factory.CacheType;
import cache.realization.UserCache;
import cache.realization.simple.CategoryCache;
import cache.realization.simple.OrderCache;
import cache.realization.simple.OrderDetailsCache;
import dao.entity.Category;
import dao.entity.Courier;
import dao.entity.Order;
import dao.entity.OrderDetails;
import dao.entity.Product;
import dao.entity.User;
import exceptions.IdTypeException;
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

	
	//Here stored every cache in my application
	   private static Cache userCache;

	   private static Cache orderCache;

	   private static Cache odCache;
 
	   private static Cache productCache;
	   
	   private static Cache suplierCache;
	   
	   private static Cache roleCache;
	   
	   private static Cache categoryCache;
    //
	
	/**
	 * @param user
	 * @return
	 */
	public boolean registerUser(User user) {
		userCache = lazyInit(userCache, CacheType.USER);
		return userCache.save(user);
	}

	/**
	 * 
	 * @param user
	 * @return boolean if true then authorization passed
	 * @throws NullPointerException
	 *             if user == null
	 */
	public boolean authorization(User user) {
		if (user == null) {
			throw new NullPointerException("You can not authorize with null");
		}
		userCache = lazyInit(userCache, CacheType.USER);
		return ((UserCache) userCache).authorization(user);
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
		orderCache = lazyInit(orderCache, CacheType.ORDER);
		odCache = lazyInit(odCache, CacheType.ORDERDETAILS);
		productCache = lazyInit(productCache, CacheType.PRODUCT);
		int orderid = -1;
		logger.debug(shoppingCard);
		logger.debug(shoppingCard.getUser());
		logger.debug(shoppingCard.getUser().getId());
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
		productCache = lazyInit(productCache, CacheType.PRODUCT);
		return productCache.getAllByCriteria("categoryid", categoryid);
	}

	/**
	 * saving time of sending to database and cache
	 * 
	 * @param courier
	 * @param order
	 */
	public void sendProdutsToClient(Courier courier, Order order) {
		orderCache = lazyInit(orderCache, CacheType.ORDER);
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
	
	/*
	 * I have to return Object.class there but it either String or Integer
	 */
	
	/**
	 * if it is user type then is should be String,
	 * if it is not user type then id should be Integer 
	 * @param type
	 * @param id
	 * @return The concrete Entity by id
	 * @throws IdTypeException if class of id is correct
	 */
	public Object getById(CacheType type, Object id) {
		//Checking on correct id class
		// I know, not flexible, but I did what I could
		if(type.equals(CacheType.USER)) {
			if(!(id instanceof String)) {
			    throw new IdTypeException(type,id);
			}
		} else {
			if(!(id instanceof Integer)) {
				throw new IdTypeException(type,id);
			}
		}
		Cache cache = getCacheFromType(type);
		return cache.getById(id);
	}
	
	public List<Object> getAll(CacheType type){
		Cache cache = getCacheFromType(type);
		return cache.getAll();
	}
	
	/**
	 * 
	 * @param type
	 * @return
	 * @throws NullPointerException
	 */
	private Cache getCacheFromType(CacheType type) {
		switch (type) {
		case USER:
		    userCache = lazyInit(userCache, CacheType.USER);
			return userCache;
		case PRODUCT:
			productCache = lazyInit(productCache, CacheType.PRODUCT);
			return productCache;
		case ORDER:
			orderCache = lazyInit(orderCache, CacheType.ORDER);
			return orderCache;
		case ORDERDETAILS:
			odCache = lazyInit(odCache, CacheType.ORDERDETAILS);
			return  odCache;
		case SUPLIER:
			suplierCache = lazyInit(suplierCache, CacheType.SUPLIER);
			return suplierCache;
		case ROLE:
			roleCache = lazyInit(roleCache, CacheType.ROLE);
			return roleCache;
		case CATEGORY:
			categoryCache = lazyInit(categoryCache, CacheType.CATEGORY);
			return categoryCache;
		default:
			throw new NullPointerException("There is not a such type of Cache!");
		}
	}

}
