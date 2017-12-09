package cache.realization.simple;

import cache.realization.SimpleLoading;
import dao.entity.Product;
import dao.entity.Suplier;

public class SuplierCache extends SimpleLoading<Integer, Suplier>{

	public SuplierCache(Class<Suplier> type) {
		super(type);
	}

	
}
