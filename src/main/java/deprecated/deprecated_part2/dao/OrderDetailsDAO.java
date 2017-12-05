package deprecated.deprecated_part2.dao;

import java.util.ArrayList;
import java.util.List;

import dao.entity.OrderDetails;
import dao.entity.Product;
import deprecated.deprecated_part2.dao.superb.AbstractDAO;

public class OrderDetailsDAO extends AbstractDAO<OrderDetails, Integer>{

	@Override
	public String getSelectQuery() {
		return "select * from ORDERDETAILS";
	}

	@Override
	public String getUpdateQuery(OrderDetails entity) {
		return "update ORDERDETAILS set orderid =" + entity.getOrderid() + ", userid = " + entity.getProductid() + " , price = " + entity.getPrice() + " , quantity = " + entity.getQuantity() + "discount = " + entity.getDiscount() + " where orderdetailsid = " + entity.getOrderdetailsid();
	}

	@Override
	public String getDeleteQuery(OrderDetails entity) {
		return "delete from ORDERDETAILS where = " + entity.getOrderdetailsid();
	}

	@Override
	public String getInsertQuery(OrderDetails entity) {
		return "insert into orderdetailsid(orderid, userid , price , quantity , discount) values(" + entity.getOrderid() + "," + entity.getProductid() + ", " + entity.getPrice() + "," + entity.getQuantity() + "," + entity.getDiscount() + ")";
	}

	@Override
	public List<OrderDetails> parseObjectsToList(Object[][] obs) {
		List<OrderDetails> list = new ArrayList<>();
		for(int i =0 ; i< obs[0].length;i++) {
			list.add(new OrderDetails((int)obs[0][0],(int) obs[0][1],(long) obs[0][2],(int) obs[0][3],(float) obs[0][4]));
		}
		return list;
	}

	@Override
	public OrderDetails parseObjectsToEntity(Object[][] obs) {
		return new OrderDetails((int)obs[0][0],(int) obs[0][1],(long) obs[0][2],(int) obs[0][3],(float) obs[0][4]);
	}

	
}
