package cache;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import dao.MyDAO;
import dao.annotation.MyEntity;
import dao.entity.User;

public abstract class Cache<K, E> {
	
	private final Logger logger = Logger.getLogger(Cache.class);

	protected int DEFAULT_MAX_SIZE = 64;
	
	protected final static int DEFAULT_CAPACITY = 16;
	
	private String idName;
	
	private Field fieldWithKey;

	protected ConcurrentHashMap<K, E> cache;

	protected MyDAO<E, K> dao;

	Class<E> type;

	public Cache(Class<E> type) {
		this(type,DEFAULT_CAPACITY);
		
	}
	
	public Cache(Class<E> type,int capacity) {
		this.type = type;
		declareMapAndDAO(capacity);
		load();
		if(type.equals(User.class)) {
			idName = "email";
		} else {
			MyEntity annotationEntity = type.getAnnotation(MyEntity.class);
		    idName = annotationEntity.id();
		}
		try {
			fieldWithKey = type.getDeclaredField(idName);
			fieldWithKey.setAccessible(true);
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
	
	protected abstract void load();

	protected void declareMapAndDAO(int capacity) {
		cache = new ConcurrentHashMap<>(capacity);
		dao = new MyDAO<>(type);
	}

	public E get(K id) {
		if(cache.size() >= DEFAULT_MAX_SIZE) {
			cache = new ConcurrentHashMap<>();
		}
		E entity = cache.get(id);
		if(entity == null) {
			entity = dao.findByCriteria(idName, id);
			logger.debug(entity + "is not in cach");
			if(entity == null) {
				logger.debug(entity + "is not in database");
				return null;
			} else {
				cache.put(id, entity);
				logger.debug(entity + "is in cach");
			}
			
		} 
			return entity;
	}
	
	public List<E> getAllByCriteria(String name, Object like){
		if(cache.size() >= DEFAULT_MAX_SIZE) {
			cache = new ConcurrentHashMap<>();
		}
		List<E> list = dao.getAllByCriteria(name, like);
		return list;
	}

	public  boolean update(E entity) {
		if (dao.update(entity)) {
			K key = null;
			try {
				key = (K) fieldWithKey.get(entity);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				return false;
			}
			// from doc - 
			// This method does nothing if the key is not in the map.
			cache.remove(key);
			//
		    cache.put(key, entity);
			return true;
		} else {
			return false;
		}
	}

	public boolean delete(E entity) {
		if (dao.delete(entity)) {
			try {
				cache.remove(fieldWithKey.get(entity));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
            return false;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @param entity
	 * @return True if it was saved in database.It does not depend on cache
	 */
	public boolean save(E entity) {
		if(cache.size() >= DEFAULT_MAX_SIZE) {
			cache = new ConcurrentHashMap<>();
		}
		if (dao.save(entity)) {
			try {
				cache.put((K) fieldWithKey.get(entity), entity);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
				logger.error(e.getMessage() + type.getSimpleName() + " was saved in database, but was not saved in cache");
			}
			return true;
		} else {
		   return false;
		}
	}

	/**
	 * DEFAULT_MAX_SIZE by defualt equals 16
	 * It checks if the parameter more than zero
	 * @param max_size
	 */
	public void setMaxSize(int max_size) {
		if(max_size < 0) {
			logger.warn("The parameter in method setMaxSize was less then zero!");
			return;
		}
		DEFAULT_MAX_SIZE = max_size;
	}
	
	

}
