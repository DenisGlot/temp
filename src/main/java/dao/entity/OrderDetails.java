package dao.entity;

public class OrderDetails {

    private int orderdetailsid;
    
    private int orderid;
    
    private int userid;
    
    private long price;
    
    private int quantity;
    
    private float discount;

    public OrderDetails() {}        
    
	public OrderDetails(int orderid, int userid, long price, int quantity, float discount) {
		this.orderid = orderid;
		this.userid = userid;
		this.price = price;
		this.quantity = quantity;
		this.discount = discount;
	}

	public int getOrderdetailsid() {
		return orderdetailsid;
	}

	public void setOrderdetailsid(int orderdetailsid) {
		this.orderdetailsid = orderdetailsid;
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

	public long getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}
    
    
   
}
