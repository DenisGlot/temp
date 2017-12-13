package dao.entity;

import java.sql.Timestamp;

import dao.annotation.MyColumn;
import dao.annotation.MyEntity;

@MyEntity(id="courierid", tableName = "COURIERS" )
public class Courier {

	@MyColumn(clazz = Integer.class, columnName = "courierid")
	private Integer courierid;
	
	@MyColumn(clazz = String.class, columnName = "firstname")
	private String firstname;
	
	@MyColumn(clazz = String.class, columnName = "lastname")
	private String lastname;
	
	@MyColumn(clazz = Timestamp.class, columnName = "hiredate")
	private Timestamp hiredate;
	
	@MyColumn(clazz = Timestamp.class, columnName = "birth")
	private Timestamp birth;

	
	
	public Courier() {}

	public Courier(Integer courierid, String firstname, String lastname, Timestamp hiredate, Timestamp birth) {
		this.courierid = courierid;
		this.firstname = firstname;
		this.lastname = lastname;
		this.hiredate = hiredate;
		this.birth = birth;
	}
	
	
}
