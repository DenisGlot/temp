package dao.entity;

import java.sql.Timestamp;

import dao.annotation.MyColumn;
import dao.annotation.MyEntity;

@MyEntity(tableName = "ACCESS",id = "id")
public class User {
	
	@MyColumn(columnName="id",clazz = Integer.class)
	private Integer id;
	
	@MyColumn(columnName = "firstname",clazz = String.class)
	private String firstname;
	
	@MyColumn(columnName = "lastname",clazz = String.class)
	private String lastname;
	
	@MyColumn(columnName = "dateofbirth",clazz = Timestamp.class)
	private Timestamp dateofbirth;
	
	@MyColumn(columnName = "address",clazz = String.class)
	private String address;

	@MyColumn(columnName = "email",clazz = String.class)
	private String email;
	
	@MyColumn(columnName = "password",clazz = String.class)
	private String password;
	
	@MyColumn(columnName = "groupid",clazz = Integer.class)
	private Integer groupid;

	public User() {
		
	}
	
	public User(String email, String password) {
		this.email = email;
		this.password = password;
	}
	
	public User(int id, String email, String password) {
		this.id = id;
		this.email = email;
		this.password = password;
	}
	
	public User(String email, String password, int groupid) {
		this.email = email;
		this.password = password;
		this.groupid = groupid;
	}
	
	public User(String firstname, String lastname, Timestamp dateOfBirth, String address, String email, String password,
			int groupid) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.dateofbirth = dateOfBirth;
		this.address = address;
		this.email = email;
		this.password = password;
		this.groupid = groupid;
	}
	
	public User(Integer id, String firstname, String lastname, Timestamp dateOfBirth, String address, String email,
			String password, Integer groupid) {
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.dateofbirth = dateOfBirth;
		this.address = address;
		this.email = email;
		this.password = password;
		this.groupid = groupid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstname;
	}

	public void setFirstName(String firstname) {
		this.firstname = firstname;
	}

	public String getLastName() {
		return lastname;
	}

	public void setLastName(String lastname) {
		this.lastname = lastname;
	}

	public Timestamp getDateOfBirth() {
		return dateofbirth;
	}

	public void setDateOfBirth(Timestamp dateOfBirth) {
		this.dateofbirth = dateOfBirth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

	public Integer getGroupid() {
		return groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		User other = (User) obj;
		if (id != other.id)
			return false;
		return true;
	}

	
	
}
