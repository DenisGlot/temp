package dao;

import java.util.List;

import dao.entity.User;

/**
 * Here K is Primary Key and E is entity
 * @author Denis
 *
 * @param <E>
 * @param <K>
 */
public interface DAO<E,K> {
    List<E> getAll();
    List<E> getAllByCriteria(String name,Object like);
    E findById(K id);
    boolean update(E entity);
    boolean delete(E entity);
    E findByCriteria(String name,Object like);
    boolean save(E entity);
    /**
     * For the fast authorization
     * @param user
     * @return
     */
    boolean checkInDataBase(User user);
}
