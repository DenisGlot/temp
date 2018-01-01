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
	
	/**
	 * Method checkInDatBase is locked on JdbcTemplate field.
	 * It does not move through application the real password of client
	 * due to PrepareStatement with sql - select 'OK' from ACCESS where email = ? and password = ?
	 * @param user the client who wants to in
	 * @return authorized or not authorized
	 */
	public boolean authorization(User user) {
		return dao.checkInDataBase(user);
	}
	
}
