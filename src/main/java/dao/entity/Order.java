package dao.entity;

import java.sql.Date;
import java.sql.Timestamp;

public class Order {

	private int orderid;
	
	private int userid;
	
	private Timestamp orderDate;
	
	private Timestamp shipperedDate;

	public Order() {}
	
	public Order(int userid, Timestamp orderDate, Timestamp shipperedDate) {
		this.userid = userid;
		this.orderDate = orderDate;
		this.shipperedDate = shipperedDate;
	}

	public int getOrderid() {
		return orderid;
	}

	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public Timestamp getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Timestamp orderDate) {
		this.orderDate = orderDate;
	}

	public Timestamp getShipperedDate() {
		return shipperedDate;
	}

	public void setShipperedDate(Timestamp shipperedDate) {
		this.shipperedDate = shipperedDate;
	}  
}
