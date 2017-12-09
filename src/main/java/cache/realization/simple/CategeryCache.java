package cache.realization.simple;

import cache.realization.SimpleLoading;
import dao.entity.Category;

public class CategeryCache extends SimpleLoading<Integer, Category>{

	public CategeryCache(Class<Category> type) {
		super(type);
	}

	
}
