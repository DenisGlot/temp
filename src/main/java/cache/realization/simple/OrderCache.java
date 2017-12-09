package cache.realization.simple;

import cache.Cache;
import cache.realization.SimpleLoading;
import dao.entity.Order;

public class OrderCache extends SimpleLoading<Integer,Order> {

	public OrderCache(Class<Order> type) {
		super(type);
	}

}
