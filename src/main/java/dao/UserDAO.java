package dao;

import java.util.ArrayList;
import java.util.List;

import dao.entity.User;
import hash.Hashing;

public class UserDAO extends AbstractDAO<User, Integer>{

	@Override
	public String getSelectQuery() {
		return "select * from ACCESS";
	}

	@Override
	public String getUpdateQuery(User entity) {
		return "update ACCESS set email = '" + entity.getEmail() + "',password ='" + Hashing.sha1(entity.getPassword()) + "' where id =" + entity.getId();
	}

	@Override
	public String getDeleteQuery(User entity) {
		return "delete from ACCESS where id =" + entity.getId();
	}

	@Override
	public String getInsertQuery(User entity) {
		return "insert into ACCESS(email,password) values ('" + entity.getEmail() + "','" + Hashing.sha1(entity.getPassword()) + "')";
	}

	@Override
	public List<User> parseObjectsToList(Object[][] obs) {
		List<User> list = new ArrayList<>();
		for(int i =0 ; i< obs[0].length;i++) {
			list.add(new User((Integer)obs[i][0],(String) obs[i][1],(String) obs[i][2]));
		}
		return list;
	}

	@Override
	public User parseObjectsToEntity(Object[][] obs) {
		return new User((Integer)obs[0][0],(String)obs[0][1],(String)obs[0][2]);
	}

	
	
}
