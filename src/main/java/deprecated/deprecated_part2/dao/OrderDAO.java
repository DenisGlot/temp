package deprecated.deprecated_part2.dao;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import dao.entity.Order;
import dao.entity.User;
import deprecated.deprecated_part2.dao.superb.AbstractDAO;
import hash.Hashing;

public class OrderDAO extends AbstractDAO<Order, Integer>{

	@Override
	public String getSelectQuery() {
		return "select * from ORDERS";
	}

	@Override
	public String getUpdateQuery(Order entity) {
		return "update ACCESS set userid = " + entity.getUserid() + ", orderdate = '" + entity.getOrderDate() + "', shippereddate = '" + entity.getShipperedDate() + "' where id =" + entity.getOrderid();
	}

	@Override
	public String getDeleteQuery(Order entity) {
		return "delete from ORDERS where orderid = " + entity.getOrderid();
	}

	@Override
	public String getInsertQuery(Order entity) {
		return "insert into ORDERS(userid, orderdate,shippereddate) values(" + entity.getUserid() + ", " + entity.getOrderDate() + ", + " + entity.getShipperedDate() + ") ";
	}

	@Override
	public List<Order> parseObjectsToList(Object[][] obs) {
		List<Order> list = new ArrayList<>();
		for(int i =0 ; i< obs[0].length;i++) {
			list.add(new Order((int) obs[i][0],(Timestamp) obs[i][1],(Timestamp) obs[i][2]));
		}
		return list;
	}

	@Override
	public Order parseObjectsToEntity(Object[][] obs) {
		return new Order((int) obs[0][0],(Timestamp) obs[0][1],(Timestamp) obs[0][2]);
	}
      
}
