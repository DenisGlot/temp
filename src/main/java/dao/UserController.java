package dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dao.entity.User;
import hash.Hashing;
import jdbc.JdbcTemplate;

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
		return new User((Integer)obs[0][0],(String)obs[0][1],(String)obs[0][2]);
	}

	@Override
	public boolean update(User entity) {
		return jt.executeDDL("update ACCESS set email = '" + entity.getEmail() + "',password ='" + Hashing.sha1(entity.getPassword()) + "' where id =" + entity.getId()); 
	}
	
	@Override
	public boolean delete(User entity) {
		return jt.executeDDL("delete from ACCESS where id =" + entity.getId());
	}

	@Override
	public User findByCriteria(String name,String like) {
		Object[][] obs = jt.executePreparedSelect("select * from ACCESS where " + name + " = ?", like);
		User user = new User((Integer)obs[0][0],(String)obs[0][1],(String)obs[0][2]);
		System.out.println(user);
		return user;
	}

	@Override
	public boolean save(User entity) {
	    return jt.executeDDL("insert into ACCESS(email,password) values ('" + entity.getEmail() + "','" + Hashing.sha1(entity.getPassword()) + "')");
	}

}
