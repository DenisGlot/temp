package dao;

import java.util.List;

import jdbc.JdbcTemplate;

public abstract class AbstractDAO<E,K> implements DAO<E,K> {
	
	JdbcTemplate jt = null;
	
	public AbstractDAO() {
	   jt = new JdbcTemplate();
    }

	/**
     * ¬озвращает sql запрос дл€ получени€ всех записей.
     * <p/>
     * SELECT * FROM [Table]
     */
    public abstract String getSelectQuery();
    
    /**
     * ¬озвращает sql запрос дл€ дл€ обновлени€ записи.
     * <p/>
     * SELECT * FROM [Table]
     */
    public abstract String getUpdateQuery(E entity);
    
    /**
     * ¬озвращает sql запрос дл€ удалени€ записи.
     * <p/>
     * SELECT * FROM [Table]
     */
    public abstract String getDeleteQuery(E entity);
    
    /**
     * ¬озвращает sql запрос дл€ вставки новой записи в базу данных.
     * <p/>
     * SELECT * FROM [Table]
     */
    public abstract String getInsertQuery(E entity);
    
    /**
     * Parses array of arrays to list of Entity
     * @param obs, array of arrays which created in JdbcTemlate
     * @return
     */
    public abstract List<E> parseObjectsToList(Object[][] obs);
    
    /**
     * Parses first row array of arrays to Entity
     * @param obs , array of arrays which created in JdbcTemlate
     * @return
     */
    public abstract E parseObjectsToEntity(Object[][] obs);
    
    @Override
	public List<E> getALl() {
        String sql = getSelectQuery();
        Object[][] obs = jt.executeSelect(sql);
        List<E> list = parseObjectsToList(obs);
        return list;
    }
    
    @Override
	public E findById(K id) {
        String sql = getSelectQuery();
        sql += " WHERE id = ?";
        Object[][] obs = jt.executePreparedSelect(sql,id);
        if (obs == null) {
			return null;
		} 
        E entity = parseObjectsToEntity(obs);  
        return entity;
    }
    
    @Override
	public boolean update(E entity) {
    	String sql = getUpdateQuery(entity);
    	return jt.executeDDL(sql);
    }
    
    @Override
	public boolean delete(E entity) {
    	String sql = getDeleteQuery(entity);
    	return jt.executeDDL(sql);
    }
    
    @Override
	public E findByCriteria(String name, String like) {
    	String sql = getSelectQuery();
        sql += " WHERE " + name +" = ?";
        Object[][] obs = jt.executePreparedSelect(sql,like);
        E entity = parseObjectsToEntity(obs);
        return entity;
    }
    
    @Override
	public boolean save(E entity) {
    	String sql = getInsertQuery(entity);
    	return jt.executeDDL(sql);
    }
}
