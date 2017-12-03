package dao.superb;

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
    boolean update(E entity);
    boolean delete(E entity);
    E findByCriteria(String name,String like);
    boolean save(E entity);
}
