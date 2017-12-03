package dao;

import java.util.ArrayList;
import java.util.List;

import dao.entity.Category;
import dao.entity.Product;
import dao.superb.AbstractDAO;

public class CategoryDAO extends AbstractDAO<Category, Integer>{

	@Override
	public String getSelectQuery() {
		return "select * from CATEGORIES";
	}

	@Override
	public String getUpdateQuery(Category entity) {
		return "update CATEGORIES set name = '" + entity.getName() + "' , description = '" + entity.getDescription() + "' where categoryid = " + entity.getCategoryid();
	}

	@Override
	public String getDeleteQuery(Category entity) {
		return "delete from CATEGORIES where categoryid = " + entity.getCategoryid();
	}

	@Override
	public String getInsertQuery(Category entity) {
		return "insert into CATEGORIES(name,description) values('" + entity.getName() + "','" + entity.getDescription() + "')";
	}

	@Override
	public List<Category> parseObjectsToList(Object[][] obs) {
		List<Category> list = new ArrayList<>();
		for(int i =0 ; i< obs[0].length;i++) {
			list.add(new Category((String) obs[0][0],(String) obs[0][1]));
		}
		return list;
	}

	@Override
	public Category parseObjectsToEntity(Object[][] obs) {
		return new Category((String) obs[0][0],(String) obs[0][1]);
	}

	
	
}
