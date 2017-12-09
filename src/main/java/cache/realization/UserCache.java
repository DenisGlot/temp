package cache.realization;

import cache.Cache;
import dao.entity.User;

public class UserCache extends Cache<String,User> {
	
	public UserCache(Class<User> type) {
		super(type);
	}

	@Override
	protected void load() {
		 cache.put("admin",dao.findByCriteria("email", "admin"));
		 cache.put("iliya",dao.findByCriteria("email", "iliya"));
		 cache.put("denis",dao.findByCriteria("email", "denis"));
	}
	
}
