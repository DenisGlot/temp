package deprecated.deprecated_part2.dao.superb;

import java.util.List;

import dao.superb.DAO;
import jdbc.JdbcTemplate;

public abstract class AbstractDAO<E,K> implements DAO<E,K> {
	
	JdbcTemplate jt = null;
	
	public AbstractDAO() {
	   jt = new JdbcTemplate();
    }

	/**
     * Returns the sql query to retrieve all rows
     * <p/>
     * SELECT * FROM [Table]
     */
    public abstract String getSelectQuery();
    
    /**
     *  Returns the sql query to update a entity in database
     * 
     */
    public abstract String getUpdateQuery(E entity);
    
    /**
     *  Returns the sql query to delete a entity in database
    
     */
    public abstract String getDeleteQuery(E entity);
    
    /**
     *  Returns the sql query to insert a entity in database
    
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
        if (obs == null || obs[0][0] == null) {
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
        System.out.println(sql);
        Object[][] obs = jt.executePreparedSelect(sql,like);
        if(obs == null || obs[0][0] == null) {
        	
        	System.out.println("Returns null");
        	return null;
        }
        
        E entity = parseObjectsToEntity(obs);
        return entity;
    }
    
    @Override
	public boolean save(E entity) {
    	String sql = getInsertQuery(entity);
    	return jt.executeDDL(sql);
    }
}
