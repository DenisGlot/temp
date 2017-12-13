package dao.entity;

import java.sql.Date;
import java.sql.Timestamp;

import dao.annotation.MyColumn;
import dao.annotation.MyEntity;

@MyEntity(id = "orderid", tableName = "ORDERS")
public class Order {

	@MyColumn(clazz = Integer.class, columnName = "orderid")
	private Integer orderid;
	
	@MyColumn(clazz = Integer.class, columnName = "userid")
	private Integer userid;
	
	@MyColumn(clazz = Integer.class, columnName = "courierid")
	private Integer courirerid;
	
	@MyColumn(clazz = Timestamp.class, columnName = "orderdate")
	private Timestamp orderDate;
	
	@MyColumn(clazz = Timestamp.class, columnName = "shippereddate")
	private Timestamp shipperedDate;

	public Order() {}
	
	
	public Order(Integer orderid, Integer userid, Integer courirerid, Timestamp orderDate, Timestamp shipperedDate) {
		super();
		this.orderid = orderid;
		this.userid = userid;
		this.courirerid = courirerid;
		this.orderDate = orderDate;
		this.shipperedDate = shipperedDate;
	}


	public Integer getOrderid() {
		return orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
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
	
	public Integer getCourirerid() {
		return courirerid;
	}

	public void setCourirerid(Integer courirerid) {
		this.courirerid = courirerid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((orderDate == null) ? 0 : orderDate.hashCode());
		result = prime * result + ((orderid == null) ? 0 : orderid.hashCode());
		result = prime * result + ((shipperedDate == null) ? 0 : shipperedDate.hashCode());
		result = prime * result + ((userid == null) ? 0 : userid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (orderDate == null) {
			if (other.orderDate != null)
				return false;
		} else if (!orderDate.equals(other.orderDate))
			return false;
		if (orderid == null) {
			if (other.orderid != null)
				return false;
		} else if (!orderid.equals(other.orderid))
			return false;
		if (shipperedDate == null) {
			if (other.shipperedDate != null)
				return false;
		} else if (!shipperedDate.equals(other.shipperedDate))
			return false;
		if (userid == null) {
			if (other.userid != null)
				return false;
		} else if (!userid.equals(other.userid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Order [orderid=" + orderid + ", userid=" + userid + ", orderDate=" + orderDate + ", shipperedDate="
				+ shipperedDate + "]";
	}
	
	
}
