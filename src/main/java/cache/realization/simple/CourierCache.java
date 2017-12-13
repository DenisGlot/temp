package cache.realization.simple;

import cache.realization.SimpleLoading;
import dao.entity.Courier;

public class CourierCache extends SimpleLoading<Integer,Courier> {

	public CourierCache(Class<Courier> type) {
		super(type);
	}

}
