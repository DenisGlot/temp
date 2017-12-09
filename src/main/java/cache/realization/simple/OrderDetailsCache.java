package cache.realization.simple;

import cache.realization.SimpleLoading;
import dao.entity.OrderDetails;

public class OrderDetailsCache extends SimpleLoading<Integer,OrderDetails> {

	public OrderDetailsCache(Class<OrderDetails> type) {
		super(type);
	}

}
