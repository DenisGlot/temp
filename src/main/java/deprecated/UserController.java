package deprecated;

import java.util.ArrayList;
import java.util.List;

import dao.DAO;
import dao.entity.User;
import hash.Hashing;
import jdbc.JdbcTemplate;

/**
 * This class is deprecated 
 * The new one to use is DAOImpl 
 * @author Denis
 *
 */
public class UserController implements DAO<User,Integer> {

	JdbcTemplate jt;
	
	public UserController() {
		jt = new JdbcTemplate();
	}
	
	@Override
	public List<User> getALl() {
		List<User> list = new ArrayList<User>();
		Object[][] obs = jt.executeSelect("select * from ACCESS");
		for(int i =0 ; i< obs[0].length;i++) {
			list.add(new User((Integer)obs[i][0],(String) obs[i][1],(String) obs[i][2]));
		}
		return list;
	}

	@Override
	public User findById(Integer id) {
		Object[][] obs = jt.executeSelect("select * from ACCESS where id ="+ id); 
		if(obs==null) {
			return null;
		}
		return new User((Integer)obs[0][0],(String)obs[0][1],(String)obs[0][2]);
	}

	@Override
	public boolean update(User entity) {
		if(findById(entity.getId())==null) {
			return false;
		} else {
		    return jt.executeDDL("update ACCESS set email = '" + entity.getEmail() + "',password ='" + Hashing.sha1(entity.getPassword()) + "' where id =" + entity.getId());
		}
	}
	
	@Override
	public boolean delete(User entity) {
		if(findById(entity.getId())==null) {
			return false;
		} else {
		   return jt.executeDDL("delete from ACCESS where id =" + entity.getId());
		}
	}

	@Override
	public User findByCriteria(String name,String like) {
		Object[][] obs = jt.executePreparedSelect("select * from ACCESS where " + name + " = ?", like);
		if(obs==null) {
			return null;
		}
		if(obs[0][0] ==null || obs[0][1]==null || obs[0][2] == null) {
			return null;
		}
		User user = new User((Integer)obs[0][0],(String)obs[0][1],(String)obs[0][2]);
		return user;
	}

	@Override
	public boolean save(User entity) {
		if(findByCriteria("email",entity.getEmail())==null) {
	       return jt.executeDDL("insert into ACCESS(email,password) values ('" + entity.getEmail() + "','" + Hashing.sha1(entity.getPassword()) + "')");
		}
		return false;
	}
	
}
