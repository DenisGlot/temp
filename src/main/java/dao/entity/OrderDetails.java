package dao.entity;

public class OrderDetails {

    private int orderdetailsid;
    
    private int orderid;
    
    private int productid;
    
    private long price;
    
    private int quantity;
    
    private float discount;

    public OrderDetails() {}        
    
	public OrderDetails(int orderid, int productid, long price, int quantity, float discount) {
		this.orderid = orderid;
		this.productid = productid;
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

	

	public int getProductid() {
		return productid;
	}

	public void setProductid(int productid) {
		this.productid = productid;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
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
