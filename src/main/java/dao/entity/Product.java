package dao.entity;

public class Product {
	
	private Integer productid;
	
	private int suplierid;
	
	private int categoryid;
	
	private String name;
	
	private String description;
	
	private long price;
	
	private int quantity;
	
	public Product() {}

	public Product(Integer productid, int manufacturerid, String name, String description, long price, int quantity) {
		super();
		this.productid = productid;
		this.suplierid = manufacturerid;
		this.name = name;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
	}

	public Integer getProductid() {
		return productid;
	}

	public void setProductid(Integer productid) {
		this.productid = productid;
	}

	public int getSuplierid() {
		return suplierid;
	}

	public void setSuplierid(int suplierid) {
		this.suplierid = suplierid;
	}
	

	public int getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(int categoryid) {
		this.categoryid = categoryid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
	

}
