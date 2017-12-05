package dao;

import java.util.ArrayList;
import java.util.List;

import dao.entity.Role;
import dao.entity.User;
import dao.superb.AbstractDAO;
import hash.Hashing;

public class RoleDAO extends AbstractDAO<Role, Integer>{

	@Override
	public String getSelectQuery() {
		return "select * from ROLES";
	}

	@Override
	public String getUpdateQuery(Role entity) {
		return "update ROLES set role ='" + entity.getRole() + "' where id =" + entity.getId();
	}

	@Override
	public String getDeleteQuery(Role entity) {
		return "delete from ROLES where id =" + entity.getId();
	}

	@Override
	public String getInsertQuery(Role entity) {
		return "insert into ROLES(id, role) values(" + entity.getId() + ", '" + entity.getRole() + "')";

	}

	@Override
	public List<Role> parseObjectsToList(Object[][] obs) {
		List<Role> list = new ArrayList<>();
		for(int i =0 ; i< obs[0].length;i++) {
			list.add(new Role((int)obs[i][0],(String) obs[i][1]));
		}
		return list;
	}

	@Override
	public Role parseObjectsToEntity(Object[][] obs) {
		return new Role((int)obs[0][0],(String) obs[0][1]);
	}

}
