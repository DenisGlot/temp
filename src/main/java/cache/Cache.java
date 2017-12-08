package cache;

import java.util.concurrent.ConcurrentHashMap;

import dao.MyDAO;
import dao.entity.User;
import dao.superb.DAO;

public class Cache {
	
	private static int DEFAULT_MAX_SIZE = 50;
	
	// I made Key type of String for fast authentication;
	private ConcurrentHashMap<String, User> userCache = null;
	
	private MyDAO<User,Integer> userDAO;
	
	public Cache() {
		userCache = new ConcurrentHashMap<>();
		loadUserAdmins();
	}
	
	/**
	 * Hardcode
	 */
	private void loadUserAdmins() {
		 userDAO = new MyDAO<>(User.class);
		 userCache.put("admin",userDAO.findByCriteria("email", "admin"));
		 userCache.put("iliya",userDAO.findByCriteria("email", "iliya"));
		 userCache.put("denis",userDAO.findByCriteria("email", "denis"));
	}
	
	public ConcurrentHashMap<String, User> getUserCache(){
		return userCache;
		
	}
	
	public User loadUser(String email) {
		if(userCache.size() == DEFAULT_MAX_SIZE) {
			userCache = new ConcurrentHashMap<>();
		}
		User user = userCache.get(email);
		if(user == null) {
			user = userDAO.findByCriteria("email", email);
			if(user == null) {
				return null;
			} else {
				userCache.put(email, user);
			}
			
		} 
			return user;
	}
	/**
	 * The class checks the size in method loadUser
	 * @param size
	 * @return
	 */
	public Cache setDefaultSize(int size) {
		DEFAULT_MAX_SIZE = size;
		return this;
	}
	

}
