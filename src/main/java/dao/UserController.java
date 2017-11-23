package dao;

import java.util.ArrayList;
import java.util.List;

import dao.entity.User;
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
		Object[][] obs = jt.executeSelect("sleect * from ACCESS where id ="+ id); 
		return new User((Integer)obs[0][0],(String)obs[0][1],(String)obs[0][2]);
	}

	@Override
	public User update(User entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findByCriteria() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean save(User entity) {
		// TODO Auto-generated method stub
		return false;
		
	}

	@Override
	public User create(User entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
