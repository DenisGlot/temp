package cache.realization;

import cache.Cache;
import dao.entity.Role;

public class RoleCache extends Cache<Integer,Role>{

	public RoleCache(Class<Role> type) {
		super(type);
	}

	@Override
	protected void load() {
		cache.put(1, dao.findById(1));
		cache.put(2, dao.findById(2));
	}

	

}
