package dao;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import dao.annotation.MyColumn;
import dao.annotation.MyEntity;
import dao.entity.User;
import jdbc.DataBaseCreator;
import jdbc.JdbcTemplate;
import jdbc.SpecJdbcTemplate;

/**
 * Keep in mind that findByCriteria returns Object and getAllByCriteria returns List of Objects.
 * I add new in JdbcTemplate method for findById and findByCriteria which will be faster for returning one row
 * @author Denis
 *
 * @param <E>
 * @param <K>
 */
public class NewDAO<E,K> implements DAO<E, K> {

private final Logger logger = Logger.getLogger(NewDAO.class);
	
	private JdbcTemplate jt;
	
	private SpecJdbcTemplate sjt;
	
	private DataBaseCreator dbCreator;
	
	private String tableName;
	
	//name of id of entity in database
	private String idName;
	
	private Field[] fields;
	
	private volatile Field fieldId = null;
	
	private Class[] cArgs;
	
	private String[] nameOfColumns;
	
	private volatile Class<E> type;
	
	public NewDAO(Class<E> type) {
		//Creating DataBase
		dbCreator = new DataBaseCreator();
		//
		jt = new JdbcTemplate();
		sjt = new SpecJdbcTemplate(jt);
		this.type = type;
		MyEntity annotation  = type.getAnnotation(MyEntity.class);
		tableName = annotation.tableName();
		idName = annotation.id();
		fields = type.getDeclaredFields();
		try {
			fieldId = type.getDeclaredField(idName);
			fieldId.setAccessible(true);
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
			logger.error(e.getMessage() + " in " + type.getName());
		}
		cArgs = new Class[fields.length];
		nameOfColumns = new String[fields.length];
		int i = 0;
		for (Field field : fields) {
			field.setAccessible(true);
			MyColumn column = field.getAnnotation(MyColumn.class);
			cArgs[i] = column.clazz();
			nameOfColumns[i] = column.columnName();
		    i++;
		}
	}

	@Override
	public List<E> getAll() {
		List<E> list = new ArrayList<>();
		List<Object[]> obs = null;
		synchronized (jt) {
			obs = jt.execSelect("select * from " + tableName);	
		}
//		//TODO remove below
//		obs.forEach((i)->{
//			for(Object o : i) {
//				logger.debug(o.toString());
//			}
//		});
		Object[] obsForInstance = new Object[obs.get(0).length];
		if(obs.get(0)[0]==null) {
			logger.debug("The array of objects contains nulls with " + type.getSimpleName());
			return null;
		}
		for (int i = 0; i < obs.size(); i++) {
			for(int j = 0; j < obsForInstance.length;j++) {
				obsForInstance[j] = obs.get(i)[j];
			}
				try {
					if(obs.get(i)[0]==null) { continue;}
					list.add(type.getConstructor(cArgs).newInstance(obsForInstance));
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
					logger.error(e.getMessage()+ " in " + type.getSimpleName());
					return null;
				}
		}
		return list;
	}
	
	@Override
	public List<E> getAllByCriteria(String name,Object like){
		List<E> list = new ArrayList<>();
		List<Object[]> obs = null;
		synchronized (jt) {
			obs = jt.execPreparedSelect("select * from " + tableName + " where " + name + " = ?", like.toString());
		}
		Object[] obsForInstance = new Object[obs.get(0).length];
		if(obs.get(0)[0]==null) {
			logger.debug("The array of objects contains nulls with " + type.getSimpleName());
			return null;
		}
		for (int i = 0; i < obs.size(); i++) {
			for(int j = 0; j < obs.get(0).length;j++) {
				obsForInstance[j] = obs.get(i)[j];
			}
				try {
					if(obs.get(i)[0]==null) { continue;}
					list.add(type.getConstructor(cArgs).newInstance(obsForInstance));
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
					logger.error(e.getMessage()+ " in " + type.getSimpleName());
					return null;
				}
		}
		return list;
	}

	@Override
	public E findById(K id) {
		Object[] obs = null;
		synchronized (jt) {
			obs = jt.execSelectForOneRow("select * from " + tableName + " where " + idName + " = " + id);
		}
		if(obs[0]==null) {
			logger.debug("The array of objects contains nulls with " + type.getSimpleName());
			return null;
		}
		try {
			Constructor<E> con = type.getConstructor(cArgs);
			return con.newInstance(obs);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+ " in " + type.getSimpleName());
			return null;
		}
	
	}
	
	@Override
	public E findByCriteria(String name, Object like) {
		Object[] obs = null;
		synchronized (jt) {
			obs = jt.execPreparedSelectForOneRow("select * from " + tableName + " where " + name + " = ?",like.toString());
		}
		if(obs[0]==null) {
			logger.debug("The array of objects contains nulls with " + type.getSimpleName());
			return null;
		}
		try {
			return type.getConstructor(cArgs).newInstance(obs);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+ " in " + type.getSimpleName());
			return null;
		}
	}

	/**
	 * Check on null. If entity equals null then return false
	 */
	@Override
	public boolean update(E entity) {
		if(entity == null) {
        	logger.debug("Entity was null");
        	return false;
        }
		StringBuffer sb = new StringBuffer();
		sb.append("update " + tableName + " set ");
		int i = 1;
		for(Field field : fields) {
			if(i==1) {
				i++;
				continue;
			}
			MyColumn column = field.getAnnotation(MyColumn.class);
			String str= column.columnName();
			try {
				if(Number.class.isAssignableFrom(field.getType())) {
					sb.append(str + " = " + field.get(entity) + (i == fields.length?" ":", "));
				} else {
					sb.append(str + " = '" + field.get(entity) + (i == fields.length?"' ":"', "));
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
				logger.error(e.getMessage()+ " in " + type.getSimpleName());
			}
			i++;
		}
		try {
			sb.append("where " + idName + " = " + fieldId.get(entity));
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+ " in " + type.getSimpleName());
			return false;
		}
		synchronized (jt) {
			return jt.executeDDL(sb.toString());
		}
	}

	/**
	 * Check on null. If entity equals null then return false
	 */
	@Override
	public boolean delete(E entity) {
		if(entity == null) {
        	logger.debug("Entity was null");
        	return false;
        }
		StringBuffer sb = new StringBuffer();
		sb.append("delete from " + tableName);
		try {
			sb.append(" where " + idName + " = " + fieldId.get(entity));
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+ " in " + type.getSimpleName());
			return false;
		}
		synchronized (jt) {
			return jt.executeDDL(sb.toString());	
		}
		
	}

	/**
	 * Check on null. If entity equals null then return false
	 */
	@Override
	public boolean save(E entity) {
        if(entity == null) {
        	logger.debug("Entity was null");
        	return false;
        }
		//Creating insert statement
        StringBuffer sb = new StringBuffer();
        sb.append("insert into " + tableName + "(");
        int i = 1;
		for(String str : nameOfColumns) {
				if(!idName.equals(str)) {
       				sb.append(str + (i == nameOfColumns.length?") ":", "));
			}
			i++;
		}
		sb.append("values(");
		i = 1;
		for (Field field : fields) {
			if(i==1) {
				i++;
				continue;
			}
			try {
				if(Number.class.isAssignableFrom(field.getType()) || field.get(entity)==null) {
					sb.append(field.get(entity) + (i == fields.length?") ":", "));
				} else {
					sb.append("'" + field.get(entity) + (i == fields.length?"') ":"', "));
				}
				
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
				logger.error(e.getMessage()+ " in " + type.getSimpleName());
				return false;
			}
			i++;
		}
		logger.debug(sb.toString());
		synchronized (jt) {
			return jt.executeDDL(sb.toString());	
		}
	}
	
	@Override
	public boolean checkInDataBase(User user) {
		
		return sjt.checkInDataBase(user);
	}

}
