package dao;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import dao.entity.User;
import dao.superb.AbstractDAO;
import hash.Hashing;

public class UserDAO extends AbstractDAO<User, Integer>{

	@Override
	public String getSelectQuery() {
		return "select * from ACCESS";
	}

	@Override
	public String getUpdateQuery(User entity) {
		return "update ACCESS set firstname = '" + entity.getFirstName() +"', lastname = '" + entity.getLastName() + "', dateofbirth = '" + entity.getDateOfBirth() + "', address = '" + entity.getAddress() + "' email = '" + entity.getEmail() + "',password ='" + Hashing.sha1(entity.getPassword()) + "', groupid = " + entity.getGroupid() + " where id =" + entity.getId();
	}

	@Override
	public String getDeleteQuery(User entity) {
		return "delete from ACCESS where id =" + entity.getId();
	}

	@Override
	public String getInsertQuery(User entity) {
		return "insert into ACCESS(firstname,lastname,dateofbirth,address,email,password,groupid) values ('" + entity.getFirstName() + "', '" + entity.getLastName() + "', '" + entity.getDateOfBirth() + "','" + entity.getAddress() + "','" + entity.getEmail() + "','" + Hashing.sha1(entity.getPassword()) + "'," + entity.getGroupid()+")";
	}

	@Override
	public List<User> parseObjectsToList(Object[][] obs) {
		List<User> list = new ArrayList<>();
		for(int i =0 ; i< obs[0].length;i++) {
			list.add(new User((int)obs[i][0],(String) obs[i][1],(String) obs[i][2],(Timestamp) obs[i][3],(String) obs[i][4],(String) obs[i][5],(String) obs[i][6],(int) obs[i][7]));
		}
		return list;
	}

	@Override
	public User parseObjectsToEntity(Object[][] obs) {
		return new User((int)obs[0][0],(String) obs[0][1],(String) obs[0][2],(Timestamp) obs[0][3],(String) obs[0][4],(String) obs[0][5],(String) obs[0][6],(int) obs[0][7]);
	}

	
	
}
