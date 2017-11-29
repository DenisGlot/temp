package dao;

import java.util.ArrayList;
import java.util.List;

import dao.entity.User;
import hash.Hashing;
import jdbc.JdbcTemplate;

public class DAOImpl<E, K> implements DAO<E, K> {

	private JdbcTemplate jt;

	private Class<E> type;

	public DAOImpl(Class<E> type) {
		jt = new JdbcTemplate();
		this.type = type; 
	}

	@Override
	public List<E> getALl() {
		if (type.isAssignableFrom(User.class)) {
			List<User> list = new ArrayList<User>();
			Object[][] obs = jt.executeSelect("select * from ACCESS");
			for (int i = 0; i < obs[0].length; i++) {
				list.add(new User((Integer) obs[i][0], (String) obs[i][1], (String) obs[i][2]));
			}
			return (List<E>) list;
		} else {
			throw new IllegalArgumentException("Table doesn't exists");
		}
	}

	@Override
	public E findById(K id) {
		if (type.isAssignableFrom(User.class)) {
			Object[][] obs = jt.executeSelect("select * from ACCESS where id =" + id);
			if (obs == null) {
				return null;
			}
			return (E) new User((Integer) obs[0][0], (String) obs[0][1], (String) obs[0][2]);
		} else {
			throw new IllegalArgumentException("Type of entity doesn't exists");
		}
	}

	@Override
	public boolean update(E entity) {
		if (type.isAssignableFrom(User.class)) {
			return jt.executeDDL("update ACCESS set email = '" + ((User) entity).getId() + "',password ='"
					+ Hashing.sha1(((User) entity).getPassword()) + "' where id =" + ((User) entity).getId());
		} else {
			throw new IllegalArgumentException("Type of entity doesn't exists");
		}
	}

	@Override
	public boolean delete(E entity) {
		if (type.isAssignableFrom(User.class)) {
			return jt.executeDDL("delete from ACCESS where id =" + ((User) entity).getId());
		} else {
			throw new IllegalArgumentException("Type of entity doesn't exists");
		}
	}

	@Override
	public E findByCriteria(String name, String like) {
		if (type.isAssignableFrom(User.class)) {
			Object[][] obs = jt.executePreparedSelect("select * from ACCESS where " + name + " = ?", like);
			if (obs == null) {
				return null;
			}
			if (obs[0][0] == null || obs[0][1] == null || obs[0][2] == null) {
				return null;
			}
			User user = new User((Integer) obs[0][0], (String) obs[0][1], (String) obs[0][2]);
			return (E) user;
		} else {
			throw new IllegalArgumentException("Type of entity doesn't exists");
		}
	}

	@Override
	public boolean save(E entity) {
		if (type.isAssignableFrom(User.class)) {
			return jt.executeDDL("insert into ACCESS(email,password) values ('" + ((User) entity).getEmail() + "','"
					+ Hashing.sha1(((User) entity).getPassword()) + "')");
		} else {
			throw new IllegalArgumentException("Type of entity doesn't exists");
		}
	}

}
