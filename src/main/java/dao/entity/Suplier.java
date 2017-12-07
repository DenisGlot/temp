package dao.entity;

import dao.annotation.MyColumn;
import dao.annotation.MyEntity;

@MyEntity(id = "suplierid", tableName = "SUPLIERS")
public class Suplier {
	
	@MyColumn(clazz = Integer.class, columnName = "suplierid")
	private Integer suplierid;
	
	@MyColumn(clazz = String.class, columnName = "name")
	private String name;
	
	@MyColumn(clazz = String.class, columnName = "description")
	private String description;
	
	public Suplier() {}

	public Suplier(Integer suplierid, String name, String description) {
		this.suplierid = suplierid;
		this.name = name;
		this.description = description;
	}



	public Integer getSuplierid() {
		return suplierid;
	}

	public void setSuplierid(Integer suplierid) {
		this.suplierid = suplierid;
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
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((suplierid == null) ? 0 : suplierid.hashCode());
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
		Suplier other = (Suplier) obj;
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
		if (suplierid == null) {
			if (other.suplierid != null)
				return false;
		} else if (!suplierid.equals(other.suplierid))
			return false;
		return true;
	}
	
}
