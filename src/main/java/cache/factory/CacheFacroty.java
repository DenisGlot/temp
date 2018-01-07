package cache.factory;

import cache.Cache;
import cache.realization.RoleCache;
import cache.realization.UserCache;
import cache.realization.simple.CategoryCache;
import cache.realization.simple.OrderCache;
import cache.realization.simple.OrderDetailsCache;
import cache.realization.simple.ProductCache;
import cache.realization.simple.SuplierCache;
import dao.entity.Category;
import dao.entity.Order;
import dao.entity.OrderDetails;
import dao.entity.Product;
import dao.entity.Role;
import dao.entity.Suplier;
import dao.entity.User;

public class CacheFacroty {

     public Cache getCache(CacheType type) {
    	switch (type) {
		case USER:
			return new UserCache(User.class);
		case PRODUCT:
			return new ProductCache(Product.class);
		case ORDER:
			return new OrderCache(Order.class);
		case ORDERDETAILS:
			return new OrderDetailsCache(OrderDetails.class);
		case SUPLIER:
			return new SuplierCache(Suplier.class);
		case ROLE:
			return new RoleCache(Role.class);
		case CATEGORY:
			return new CategoryCache(Category.class);
		} 
		return null;
    	 
     }
}
