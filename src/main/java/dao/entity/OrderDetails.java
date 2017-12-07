package dao.entity;

import java.math.BigDecimal;

import dao.annotation.MyColumn;
import dao.annotation.MyEntity;

@MyEntity(id = "orderdetailsid", tableName = "ORDERDETAILS")
public class OrderDetails {

	@MyColumn(clazz = Integer.class, columnName = "orderdetailsid")
    private Integer orderdetailsid;
    
	@MyColumn(clazz = Integer.class, columnName = "orderid")
    private Integer orderid;
    
	@MyColumn(clazz = Integer.class, columnName = "productid")
    private Integer productid;
    
	@MyColumn(clazz = Long.class, columnName = "price")
    private Long price;
    
	@MyColumn(clazz = Integer.class, columnName = "quantity")
    private Integer quantity;
    
	@MyColumn(clazz = BigDecimal.class, columnName = "discount")
    private BigDecimal discount;

    public OrderDetails() {}        
    

	public OrderDetails(Integer orderdetailsid, Integer orderid, Integer productid, Long price, Integer quantity,
			BigDecimal discount) {
		this.orderdetailsid = orderdetailsid;
		this.orderid = orderid;
		this.productid = productid;
		this.price = price;
		this.quantity = quantity;
		this.discount = discount;
	}


	public Integer getOrderdetailsid() {
		return orderdetailsid;
	}

	public void setOrderdetailsid(Integer orderdetailsid) {
		this.orderdetailsid = orderdetailsid;
	}

	public Integer getOrderid() {
		return orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	public Integer getProductid() {
		return productid;
	}

	public void setProductid(Integer productid) {
		this.productid = productid;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((discount == null) ? 0 : discount.hashCode());
		result = prime * result + ((orderdetailsid == null) ? 0 : orderdetailsid.hashCode());
		result = prime * result + ((orderid == null) ? 0 : orderid.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((productid == null) ? 0 : productid.hashCode());
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
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
		OrderDetails other = (OrderDetails) obj;
		if (discount == null) {
			if (other.discount != null)
				return false;
		} else if (!discount.equals(other.discount))
			return false;
		if (orderdetailsid == null) {
			if (other.orderdetailsid != null)
				return false;
		} else if (!orderdetailsid.equals(other.orderdetailsid))
			return false;
		if (orderid == null) {
			if (other.orderid != null)
				return false;
		} else if (!orderid.equals(other.orderid))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (productid == null) {
			if (other.productid != null)
				return false;
		} else if (!productid.equals(other.productid))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "OrderDetails [orderdetailsid=" + orderdetailsid + ", orderid=" + orderid + ", productid=" + productid
				+ ", price=" + price + ", quantity=" + quantity + ", discount=" + discount + "]";
	}
	
	

}
