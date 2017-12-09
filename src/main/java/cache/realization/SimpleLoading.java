package cache.realization;

import cache.Cache;

public abstract class SimpleLoading<K,E> extends Cache<K, E> {

	public SimpleLoading(Class<E> type) {
		super(type);
	}

	@Override
	protected void load() {
		 cache.put((K)new Integer(1), dao.findById((K) new Integer(1)));		
	}

}
