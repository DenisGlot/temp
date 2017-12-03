package dao;

import java.util.ArrayList;
import java.util.List;

import dao.entity.OrderDetails;
import dao.entity.Product;
import dao.superb.AbstractDAO;

public class OrderDetailsDAO extends AbstractDAO<OrderDetails, Integer>{

	@Override
	public String getSelectQuery() {
		return "select * from ORDERDETAILS";
	}

	@Override
	public String getUpdateQuery(OrderDetails entity) {
		return "update ORDERDETAILS set orderid =" + entity.getOrderid() + ", userid = " + entity.getUserid() + " , price = " + entity.getPrice() + " , quantity = " + entity.getQuantity() + "discount = " + entity.getDiscount() + " where orderdetailsid = " + entity.getOrderdetailsid();
	}

	@Override
	public String getDeleteQuery(OrderDetails entity) {
		return "delete from ORDERDETAILS where = " + entity.getOrderdetailsid();
	}

	@Override
	public String getInsertQuery(OrderDetails entity) {
		return "insert into orderdetailsid(orderid, userid , price , quantity , discount) values(" + entity.getOrderid() + "," + entity.getUserid() + ", " + entity.getPrice() + "," + entity.getQuantity() + "," + entity.getDiscount() + ")";
	}

	@Override
	public List<OrderDetails> parseObjectsToList(Object[][] obs) {
		List<OrderDetails> list = new ArrayList<>();
		for(int i =0 ; i< obs[0].length;i++) {
			list.add(new OrderDetails((Integer)obs[0][0],(Integer) obs[0][1],(Long) obs[0][2],(Integer) obs[0][3],(Float) obs[0][4]));
		}
		return list;
	}

	@Override
	public OrderDetails parseObjectsToEntity(Object[][] obs) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
