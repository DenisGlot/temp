package dao.entity;

import java.sql.Date;

public class Order {

	private int orderid;
	
	private int productid;
	
	private int userid;
	
	private Date orderDate;
	
	private Date shipperedDate;

	public Order() {}
	
	public Order(int productid, int userid, Date orderDate, Date shipperedDate) {
		super();
		this.productid = productid;
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

	public int getProductid() {
		return productid;
	}

	public void setProductid(int productid) {
		this.productid = productid;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Date getShipperedDate() {
		return shipperedDate;
	}

	public void setShipperedDate(Date shipperedDate) {
		this.shipperedDate = shipperedDate;
	}  
}
