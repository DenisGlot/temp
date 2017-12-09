package cache.realization.simple;

import cache.realization.SimpleLoading;
import dao.entity.Product;

public class ProductCache extends SimpleLoading<Integer, Product>{

	public ProductCache(Class<Product> type) {
		super(type);
	}

	
}
