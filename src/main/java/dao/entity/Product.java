package dao.entity;

import dao.annotation.MyColumn;
import dao.annotation.MyEntity;

@MyEntity(id = "productid", tableName = "PRODUCTS")
public class Product {

	@MyColumn(clazz = Integer.class, columnName = "productid")
	private Integer productid;

	@MyColumn(clazz = Integer.class, columnName = "suplierid")
	private Integer suplierid;

	@MyColumn(clazz = Integer.class, columnName = "categoryid")
	private Integer categoryid;

	@MyColumn(clazz = Long.class, columnName = "price")
	private Long price;

	@MyColumn(clazz = Integer.class, columnName = "quantity")
	/**
	 * It is amount of products that this e-shop has 
	 */
	private Integer quantity;

	@MyColumn(clazz = String.class, columnName = "name")
	private String name;

	@MyColumn(clazz = String.class, columnName = "description")
	private String description;
	
	@MyColumn(clazz = String.class, columnName = "urlofimg")
	private String urlofimg;

	public Product() {
	}

	public Product(Integer productid,Integer categoryid, Integer suplierid, Long price, Integer quantity, String name,
			String description,String urlofimg) {
		this.productid = productid;
		this.categoryid = categoryid;
		this.suplierid = suplierid;
		this.price = price;
		this.quantity = quantity;
		this.name = name;
		this.description = description;
		this.urlofimg = urlofimg;
	}

	public Integer getProductid() {
		return productid;
	}

	public void setProductid(Integer productid) {
		this.productid = productid;
	}

	public Integer getSuplierid() {
		return suplierid;
	}

	public void setSuplierid(Integer suplierid) {
		this.suplierid = suplierid;
	}

	public Integer getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(Integer categoryid) {
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
	
	public String getUrlofimg() {
		return urlofimg;
	}

	public void setUrlofimg(String urlofimg) {
		this.urlofimg = urlofimg;
	}

	//Counts just name and productid
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((productid == null) ? 0 : productid.hashCode());
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
		Product other = (Product) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (productid == null) {
			if (other.productid != null)
				return false;
		} else if (!productid.equals(other.productid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Product [productid=" + productid + ", suplierid=" + suplierid + ", categoryid=" + categoryid
				+ ", price=" + price + ", quantity=" + quantity + ", name=" + name + "]";
	}
	
	

}
