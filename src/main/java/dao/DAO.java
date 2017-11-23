package dao;

import java.util.List;

/**
 * Here K is Primary Key and E is entity
 * @author Denis
 *
 * @param <E>
 * @param <K>
 */
public interface DAO<E,K> {
    List<E> getALl();
    E findById(K id);
    E update(E entity);
    E findByCriteria();
    boolean save(E entity);
    E create(E entity); 
}
