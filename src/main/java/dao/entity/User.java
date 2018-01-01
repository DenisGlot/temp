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
	
	@MyColumn(columnName = "phone",clazz = String.class)
	private String phone;

	@MyColumn(columnName = "email",clazz = String.class)
	private String email;
	
	@MyColumn(columnName = "password",clazz = String.class)
	private String password;
	
	@MyColumn(columnName = "groupid",clazz = Integer.class)
	private Integer groupid;

	public User() {
		
	}
	
	public User(Integer id, String firstname, String lastname, Timestamp dateofbirth, String address, String phone,
			String email, String password, Integer groupid) {
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.dateofbirth = dateofbirth;
		this.address = address;
		this.phone = phone;
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

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Timestamp getDateofbirth() {
		return dateofbirth;
	}

	public void setDateofbirth(Timestamp dateofbirth) {
		this.dateofbirth = dateofbirth;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstname == null) ? 0 : firstname.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastname == null) ? 0 : lastname.hashCode());
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
		if (firstname == null) {
			if (other.firstname != null)
				return false;
		} else if (!firstname.equals(other.firstname))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastname == null) {
			if (other.lastname != null)
				return false;
		} else if (!lastname.equals(other.lastname))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email + "]";
	}

	

	//Builder
	
	public static Builder newBuilder() {
        return new User().new Builder();
    }
	
	public class Builder {

        private Builder() {
            // private constructor
        }
        
        public Builder setId(Integer id) {
            User.this.id = id;
            return this;
        }
        
        public Builder setPhone(String phone) {
            User.this.phone = phone;
            return this;
        }
        
        public Builder setFirstName(String firstname) {
            User.this.firstname = firstname;
            return this;
        }
        
        public Builder setLastName(String lastname) {
            User.this.lastname = lastname;
            return this;
        }
        
        public Builder setEmail(String email) {
            User.this.email = email;
            return this;
        }
        
        public Builder setPassword(String password) {
            User.this.password = password;
            return this;
        }
        
        public Builder setGruopId(Integer groupid) {
            User.this.groupid = groupid;
            return this;
        }
        
        public User build() {
            return User.this;
        }
        
	}
	
}
