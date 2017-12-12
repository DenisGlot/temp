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
import jdbc.JdbcTemplate;
import jdbc.SpecJdbcTemplate;

public class MyDAO<E,K> implements DAO<E, K> {
	
	private final Logger logger = Logger.getLogger(MyDAO.class);
	
	private JdbcTemplate jt;
	
	public SpecJdbcTemplate sjt;
	
	private String tableName;
	
	private String idName;
	
	private Field[] fields;
	
	private Field fieldId = null;
	
	private Class[] cArgs;
	
	private String[] nameOfColumns;
	
	private Class<E> type;
	
	public MyDAO(Class<E> type) {
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
	public List<E> getALl() {
		List<E> list = new ArrayList<>();
		Object[][] obs = jt.executeSelect("select * from " + tableName);
		Object[] obsForInstance = new Object[obs[0].length];
		if(obs[0][0]==null) {
			logger.debug("The array of objects contains nulls with " + type.getSimpleName());
			return null;
		}
		for (int i = 0; i < obs.length; i++) {
			for(int j = 0; j < obs[0].length;j++) {
				obsForInstance[j] = obs[i][j];
			}
				try {
					if(obs[i][0]==null) { continue;}
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
		Object[][] obs = jt.executeSelect("select * from " + tableName + " where " + idName + " = " + id);
		Object[] obsForInstance = new Object[obs[0].length];
		if(obs[0][0]==null) {
			logger.debug("The array of objects contains nulls with " + type.getSimpleName());
			return null;
		}
		for(int j = 0; j < obs[0].length;j++) {
			obsForInstance[j] = obs[0][j];
		}
		try {
			Constructor<E> con = type.getConstructor(cArgs);
			return con.newInstance(obsForInstance);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+ " in " + type.getSimpleName());
			return null;
		}
	
	}
	
	@Override
	public E findByCriteria(String name, Object like) {
		Object[][] obs = jt.executePreparedSelect("select * from " + tableName + " where " + name + " = ?",like.toString());
		Object[] obsForInstance = new Object[obs[0].length];
		if(obs[0][0]==null) {
			logger.debug("The array of objects contains nulls with " + type.getSimpleName());
			return null;
		}
		for(int j = 0; j < obs[0].length;j++) {
			obsForInstance[j] = obs[0][j];
		}
		try {
			return type.getConstructor(cArgs).newInstance(obsForInstance);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+ " in " + type.getSimpleName());
			return null;
		}
	}

	@Override
	public boolean update(E entity) {
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
		return jt.executeDDL(sb.toString());
	}

	@Override
	public boolean delete(E entity) {
		StringBuffer sb = new StringBuffer();
		sb.append("delete from " + tableName);
		try {
			sb.append(" where " + idName + " = " + fieldId.get(entity));
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			logger.error(e.getMessage()+ " in " + type.getSimpleName());
			return false;
		}
		return jt.executeDDL(sb.toString());
		
	}

	@Override
	public boolean save(E entity) {
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
		return jt.executeDDL(sb.toString());
	}
	
}
