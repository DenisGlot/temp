package dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import dao.annotation.MyColumn;
import dao.annotation.MyEntity;
import dao.entity.User;
import dao.superb.DAO;
import jdbc.JdbcTemplate;

public class MyDAO<E,K> implements DAO<E, K> {
	
	private JdbcTemplate jt;
	
	private String tableName;
	
	Field[] fields;
	
	MyEntity annotation;

	private Class<E> type;
	
	public MyDAO(Class<E> type) {
		jt = new JdbcTemplate();
		this.type = type;
		annotation  = type.getAnnotation(MyEntity.class);
		tableName = annotation.tableName();
		fields = type.getDeclaredFields();
	}

	@Override
	public List<E> getALl() {
		List<E> list = new ArrayList<>();
		Object[][] obs = jt.executeSelect("select * from " + tableName);
		
		for (int i = 0; i < obs[0].length; i++) {
			try {
				list.add(type.newInstance());
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
        for (Field field : fields) {
			MyColumn column = field.getAnnotation(MyColumn.class);
			System.out.println(column.clazz().getSimpleName());
		}
		return null;
	}

	@Override
	public E findById(K id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(E entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(E entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public E findByCriteria(String name, String like) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean save(E entity) {
		// TODO Auto-generated method stub
		return false;
	}

	

}
