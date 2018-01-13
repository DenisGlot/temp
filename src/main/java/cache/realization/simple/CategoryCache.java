package cache.realization.simple;


import java.util.List;

import cache.realization.SimpleLoading;
import dao.entity.Category;

public class CategoryCache extends SimpleLoading<Integer, Category>{

	public CategoryCache(Class<Category> type) {
		super(type);
	}
}
