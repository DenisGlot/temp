package dao.entity;

import dao.annotation.MyColumn;
import dao.annotation.MyEntity;

@MyEntity(id = "categoryid", tableName = "CATEGORIES")
public class Category {

	@MyColumn(clazz = Integer.class, columnName = "categoryid")
    private Integer categoryid;
    
	@MyColumn(clazz = String.class, columnName = "name")
    private String name;
    
	@MyColumn(clazz = String.class, columnName = "description")
    private String description;
    
    public Category() {}

	public Category(Integer categoryid, String name, String description) {
		this.categoryid = categoryid;
		this.name = name;
		this.description = description;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoryid == null) ? 0 : categoryid.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Category other = (Category) obj;
		if (categoryid == null) {
			if (other.categoryid != null)
				return false;
		} else if (!categoryid.equals(other.categoryid))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Category [categoryid=" + categoryid + ", name=" + name + ", description=" + description + "]";
	}
    
	
}