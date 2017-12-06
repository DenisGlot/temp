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
import dao.superb.DAO;
import jdbc.JdbcTemplate;

public class MyDAO<E,K> implements DAO<E, K> {
	
	private final Logger logger = Logger.getLogger(MyDAO.class);
	
	private JdbcTemplate jt;
	
	private String tableName;
	
	private String idName;
	
	private Field[] fields;
	
	private Class[] cArgs;
	
	private String[] nameOfColumns;
	
	private Class<E> type;
	
	public MyDAO(Class<E> type) {
		jt = new JdbcTemplate();
		this.type = type;
		MyEntity annotation  = type.getAnnotation(MyEntity.class);
		tableName = annotation.tableName();
		idName = annotation.id();
		fields = type.getDeclaredFields();
		cArgs = new Class[fields.length];
		nameOfColumns = new String[fields.length];
		int i = 0;
		for (Field field : fields) {
			MyColumn column = field.getAnnotation(MyColumn.class);
			cArgs[i] = column.clazz();
			nameOfColumns[i] = column.columnName();
		    i++;
		}
	}

	@Override
	public List<E> getALl() {
		List<E> list = new ArrayList<>();
		Object[][] obs = jt.executeSelect("select * from " + tableName);
		Object[] obsForInstance = new Object[obs[0].length];
		
		for (int i = 0; i < obs.length; i++) {
			for(int j = 0; j < obs[0].length;j++) {
				obsForInstance[j] = obs[i][j];
			}
				try {
					list.add(type.getConstructor(cArgs).newInstance(obsForInstance));
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
					logger.error(e.getMessage());
					return null;
				}
		}
		return list;
	}

	@Override
	public E findById(K id) {
		Object[][] obs = jt.executeSelect("select * from " + tableName + " where " + idName + " = " + id);
		Object[] obsForInstance = new Object[obs[0].length];
		for(int j = 0; j < obs[0].length;j++) {
			obsForInstance[j] = obs[0][j];
		}
		try {
			Constructor<E> con = type.getConstructor(cArgs);
			return con.newInstance(obsForInstance);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return null;
		}
	
	}
	
	@Override
	public E findByCriteria(String name, String like) {
		Object[][] obs = jt.executePreparedSelect("select * from " + tableName + " where " + name + " = ?",like);
		Object[] obsForInstance = new Object[obs[0].length];
		for(int j = 0; j < obs[0].length;j++) {
			obsForInstance[j] = obs[0][j];
		}
		try {
			return type.getConstructor(cArgs).newInstance(obsForInstance);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean update(E entity) {
		StringBuffer sb = new StringBuffer();
		sb.append("update " + tableName + "set ");
		int i = 1;
		for(String str : nameOfColumns) {
			try {
				if(!idName.equals(str)) {
       				sb.append(str + " = '" + entity.getClass().getDeclaredField(str) + (i == nameOfColumns.length?"' ":"', "));
				}
			} catch (NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
			i++;
		}
	    try {
			sb.append("where " + idName + " = " + entity.getClass().getDeclaredField(idName));
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return false;
		}
		return jt.executeDDL(sb.toString());
	}

	@Override
	public boolean delete(E entity) {
		StringBuffer sb = new StringBuffer();
		sb.append("delete from " + tableName);
		try {
			sb.append(" where " + idName + " = " + entity.getClass().getDeclaredField(idName));
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return false;
		}
		return jt.executeDDL(sb.toString());
		
	}

	@Override
	public boolean save(E entity) {
        StringBuffer sb = new StringBuffer();
        sb.append("insert into " + tableName + "(");
        int i = 1;
		for(String str : nameOfColumns) {
				if(!idName.equals(str)) {
       				sb.append(str + (i == nameOfColumns.length?") ":", "));
			}
			i++;
		}
		i = 1;
		for (String str : nameOfColumns) {
			try {
				sb.append("values(" + entity.getClass().getDeclaredField(str) + (i ==nameOfColumns.length?") ":", "));
			} catch (NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				return false;
			}
			i++;
		}
		try {
			sb.append("where " + idName + " = " + entity.getClass().getDeclaredField(idName));
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return false;
		}
		return jt.executeDDL(sb.toString());
	}

}
