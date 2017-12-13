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
    List<E> getAllByCriteria(String name,Object like);
    E findById(K id);
    boolean update(E entity);
    boolean delete(E entity);
    E findByCriteria(String name,Object like);
    boolean save(E entity);
}