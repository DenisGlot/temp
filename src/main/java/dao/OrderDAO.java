package dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import dao.entity.Order;
import dao.entity.User;
import dao.superb.AbstractDAO;
import hash.Hashing;

public class OrderDAO extends AbstractDAO<Order, Integer>{

	@Override
	public String getSelectQuery() {
		return "select * from ORDERS";
	}

	@Override
	public String getUpdateQuery(Order entity) {
		return "update ACCESS set orderid = '" + entity.getOrderid() + "',productid ='" + entity.getProductid() + "', userid = " + entity.getUserid() + ", orderdate = '" + entity.getOrderDate() + "', shippereddate = '" + entity.getShipperedDate() + "' where id =" + entity.getOrderid();
	}

	@Override
	public String getDeleteQuery(Order entity) {
		return "delete from ORDERS where orderid = " + entity.getOrderid();
	}

	@Override
	public String getInsertQuery(Order entity) {
		return "insert into ORDERS(productid, userid, orderdate,shippereddate) values(" + entity.getProductid() +", " + entity.getUserid() + ", " + entity.getOrderDate() + ", + " + entity.getShipperedDate() + ") ";
	}

	@Override
	public List<Order> parseObjectsToList(Object[][] obs) {
		List<Order> list = new ArrayList<>();
		for(int i =0 ; i< obs[0].length;i++) {
			list.add(new Order((Integer)obs[i][0],(Integer) obs[i][1],(Date) obs[i][2],(Date) obs[i][3]));
		}
		return list;
	}

	@Override
	public Order parseObjectsToEntity(Object[][] obs) {
		return new Order((Integer)obs[0][0],(Integer) obs[0][1],(Date) obs[0][2],(Date) obs[0][3]);
	}
      
}
