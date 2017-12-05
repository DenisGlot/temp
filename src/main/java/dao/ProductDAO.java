package dao;

import java.util.ArrayList;
import java.util.List;

import dao.entity.Product;
import dao.superb.AbstractDAO;

public class ProductDAO extends AbstractDAO<Product, Integer> {

	@Override
	public String getSelectQuery() {
		return "select * from PRODUCTS";
	}

	@Override
	public String getUpdateQuery(Product entity) {
		return "update PRODUCTS set categoryid = " + entity.getCategoryid() + ", suplierid = " + entity.getSuplierid()  + ", price = " + entity.getPrice() + ", quantity = " + entity.getQuantity() + ", name = '" + entity.getName() + "', description = " + entity.getDescription() + " where productid = " + entity.getProductid();
	}

	@Override
	public String getDeleteQuery(Product entity) {
		return "delete from PRODUCTS where prouctid = " + entity.getProductid();
	}

	@Override
	public String getInsertQuery(Product entity) {
		return "insert into PRODUCTS(categoryid,suplierid,price,quantity,name,description) values(" + entity.getCategoryid() + ", " + entity.getSuplierid() + ", " + entity.getPrice() + ", " + entity.getQuantity() + ", name = '" + entity.getName() + "', description = '" + entity.getDescription() + "')";
	}

	@Override
	public List<Product> parseObjectsToList(Object[][] obs) {
		List<Product> list = new ArrayList<>();
		for(int i =0 ; i< obs[0].length;i++) {
			list.add(new Product((int)obs[0][0],(int) obs[0][1],(long) obs[0][2],(int) obs[0][3],(String) obs[0][4],(String) obs[0][5]));
		}
		return list;
	}

	@Override
	public Product parseObjectsToEntity(Object[][] obs) {
		return new Product((int)obs[0][0],(int) obs[0][1],(long) obs[0][2],(int) obs[0][3],(String) obs[0][4],(String) obs[0][5]);
	}

}
